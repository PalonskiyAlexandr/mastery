package com.palonskiy.serice;

import com.palonskiy.converters.AuthorConverter;
import com.palonskiy.converters.BookConverter;
import com.palonskiy.dao.AuthorDao;
import com.palonskiy.dao.BookDao;
import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.exceptions.ExistingEntityException;
import com.palonskiy.exceptions.NoResultException;
import com.palonskiy.exceptions.NullAuthorException;
import com.palonskiy.exceptions.NullBookException;
import com.palonskiy.model.Author;
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
public class BookServiceImpl implements BookService {

    private BookDao bookDao;
    private BookAuthorService bookAuthorService;
    private AuthorDao authorDao;

    public BookServiceImpl(BookDao bookDao, BookAuthorService bookAuthorService, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.bookAuthorService = bookAuthorService;
        this.authorDao = authorDao;
    }

    @Override
    public List<BookDto> get() {
        return BookConverter.toDtoList(bookDao.get());
    }

    @Override
    public BookDto getById(int id) {
        return BookConverter.toBookDto(bookDao.getById(Long.valueOf(id)));
    }

    @Override
    public void update(BookDto bookDto) {
        bookDao.update(BookConverter.toBook(bookDto));
    }

    @Override
    public List<AuthorDto> getBookAuthors(int id) {
        List<BookAuthor> bookAuthors = bookDao.getBookAuthors().stream()
                .filter(bookAuthor -> (bookAuthor.getBook() == bookDao.getById(Long.valueOf(id))))
                .collect(Collectors.toList());
        List<Author> authors = new ArrayList<>();
        for (BookAuthor bookAuthor : bookAuthors) {
            authors.add(bookAuthor.getAuthor());
        }
        return AuthorConverter.toDtoList(authors);
    }

    @Override
    public void add(BookAuthorDto bookAuthorDto) {
        if (bookAuthorDto.getBook() != null) {
            if (bookAuthorDto.getAuthor() != null) {
                if (bookDao.checkIfExist(bookAuthorDto.getBook().getName()) == null) {
                    bookDao.add(BookConverter.toBook(bookAuthorDto.getBook()));
                    Long bookId = bookDao.getId();
                    Long authorId =bookAuthorDto.getAuthor().getId();
                    if (authorDao.checkIfExist(bookAuthorDto.getAuthor().getSecondName()) == null) {
                        authorDao.add(AuthorConverter.toAuthor(bookAuthorDto.getAuthor()));
                        authorId = authorDao.getId();
                    }
                    bookAuthorService.add(bookAuthorDto, authorId, bookId);
                } else throw new ExistingEntityException();
            } else throw new NullBookException();
        } else
            throw new NullAuthorException();
    }

    @Override
    public void addOnlyBook(BookDto bookDto) {
        bookDao.add(BookConverter.toBook(bookDto));
    }

    @Override
    public Long getId() {
        return bookDao.getId();
    }

    @Override
    public void delete(int id) {
        bookDao.delete(Long.valueOf(id));
    }

    @Override
    public Book checkIfExist(String name) {
        return bookDao.checkIfExist(name);
    }

    @Override
    public List<BookAuthorDto> sortByYear(int year) {
        return Optional.of(bookAuthorService.get().stream()
                .filter(bookAuthor -> (bookAuthor.getBook().getYear().getYear() == year))
                .collect(Collectors.toList()))
                .filter(a -> !a.isEmpty())
                .orElseThrow(() -> new NoResultException());
    }

    @Override
    public List<BookAuthorDto> sortByName(String name) {
        return Optional.of(bookAuthorService.get().stream()
                .filter(bookAuthor -> (bookAuthor.getBook().getName().equals(name)))
                .collect(Collectors.toList()))
                .filter(a -> !a.isEmpty())
                .orElseThrow(() -> new NoResultException());
    }

    @Override
    public List<BookAuthorDto> sortByPublisher(String name) {
        return Optional.of(bookAuthorService.get().stream()
                .filter(bookAuthor -> (bookAuthor.getBook().getPublisher().equals(name)))
                .collect(Collectors.toList()))
                .filter(a -> !a.isEmpty())
                .orElseThrow(() -> new NoResultException());
    }
}
