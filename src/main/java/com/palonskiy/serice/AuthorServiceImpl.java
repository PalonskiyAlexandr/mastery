package com.palonskiy.serice;

import com.palonskiy.converters.AuthorConverter;
import com.palonskiy.converters.BookConverter;
import com.palonskiy.dao.AuthorDao;
import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.exceptions.ExistingEntityException;
import com.palonskiy.exceptions.NoResultException;
import com.palonskiy.exceptions.NullAuthorException;
import com.palonskiy.model.Book;
import com.palonskiy.model.BookAuthor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao;
    private BookAuthorService bookAuthorService;
    private BookService bookService;

    public AuthorServiceImpl(AuthorDao authorDao, BookAuthorService bookAuthorService, BookService bookService) {
        this.authorDao = authorDao;
        this.bookAuthorService = bookAuthorService;
        this.bookService = bookService;
    }

    @Override
    public List<AuthorDto> get() {
        return AuthorConverter.toDtoList(authorDao.get());
    }

    @Override
    public AuthorDto getById(int id) {
        return AuthorConverter.toAuthorDto(authorDao.getById(Long.valueOf(id)));
    }

    @Override
    public void update(AuthorDto authorDto) {
        authorDao.update(AuthorConverter.toAuthor(authorDto));
    }

    @Override
    public List<BookDto> getAuthorBooks(int id) {
        List<BookAuthor> authorBooks = authorDao.getAuthorBooks().stream()
                .filter(authorBook -> (authorBook.getAuthor() == authorDao.getById(Long.valueOf(id))))
                .collect(Collectors.toList());
        List<Book> books = new ArrayList<>();
        for (BookAuthor authorBook : authorBooks) {
            books.add(authorBook.getBook());
        }
        return BookConverter.toDtoList(books);
    }

    @Override
    public void add(BookAuthorDto bookAuthorDto) {
        if (bookAuthorDto.getAuthor() != null) {
            if (authorDao.checkIfExist(bookAuthorDto.getAuthor().getSecondName()) == null) {
                authorDao.add(AuthorConverter.toAuthor(bookAuthorDto.getAuthor()));
                Long authorId = authorDao.getId();
                Long bookId = bookAuthorDto.getBook().getId();
                if (bookAuthorDto.getBook() != null && bookService.checkIfExist(bookAuthorDto.getBook().getName()) == null) {
                    bookService.addOnlyBook(bookAuthorDto.getBook());
                    bookId = bookService.getId();
                }
                bookAuthorService.add(bookAuthorDto, authorId, bookId);
            } else throw new ExistingEntityException();
        } else
            throw new NullAuthorException();
    }

    @Override
    public void addOnlyAuthor(AuthorDto authorDto) {
        authorDao.add(AuthorConverter.toAuthor(authorDto));
    }

    @Override
    public Long getId() {
        return authorDao.getId();
    }

    @Override
    public void delete(int id) {
        List<Long> books = bookAuthorService.findBooksByAuthor(Long.valueOf(id));
        for (Long bookId : books) {
            if (bookAuthorService.findAuthorsByBook(bookId).size() < 2) {
                bookService.delete(Math.toIntExact(bookId));
            }
        }
        authorDao.delete(Long.valueOf(id));
    }

    @Override
    public List<BookAuthorDto> sortByBirthday(int year) {
        return Optional.of(bookAuthorService.get().stream()
                .filter(bookAuthor -> (bookAuthor.getAuthor().getBirthday().getYear() == year))
                .collect(Collectors.toList()))
                .filter(a -> !a.isEmpty())
                .orElseThrow(() -> new NoResultException());
    }

    @Override
    public List<BookAuthorDto> sortBySex(String sex) {
        return Optional.of(bookAuthorService.get().stream()
                .filter(bookAuthor -> (bookAuthor.getAuthor().getSex().toString().equals(sex)))
                .collect(Collectors.toList()))
                .filter(a -> !a.isEmpty())
                .orElseThrow(() -> new NoResultException());
    }

    @Override
    public List<BookAuthorDto> sortByName(String name) {
        return Optional.of(bookAuthorService.get().stream()
                .filter(bookAuthor -> (bookAuthor.getAuthor().getFirstName().startsWith(name)))
                .collect(Collectors.toList()))
                .filter(a -> !a.isEmpty())
                .orElseThrow(() -> new NoResultException());
    }

    @Override
    public List<BookAuthorDto> sortBySecondName(String name) {
        return Optional.of(bookAuthorService.get().stream()
                .filter(bookAuthor -> (bookAuthor.getAuthor().getSecondName().startsWith(name)))
                .collect(Collectors.toList()))
                .filter(a -> !a.isEmpty())
                .orElseThrow(() -> new NoResultException());
    }

}
