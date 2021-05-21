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
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao;
    private BookService bookService;
    private BookDao bookDao;

    public AuthorServiceImpl(AuthorDao authorDao,BookDao bookDao, BookService bookService) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.bookService = bookService;
    }

    @Override
    public List<AuthorDto> get() {
        return AuthorConverter.toDtoList(authorDao.getAll());
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
        return BookConverter.toDtoList(authorDao.getAuthorBooks(Long.valueOf(id)));
    }

    @Override
    //ультра-мусор
    public void add(BookAuthorDto bookAuthorDto) {
        if (bookAuthorDto.getAuthor() != null) {
            if (!authorDao.checkIfExist(bookAuthorDto.getAuthor()) ) {
                Author author = AuthorConverter.toAuthor(bookAuthorDto.getAuthor());
                if (bookAuthorDto.getBook() != null && !bookService.checkIfExist(bookAuthorDto.getBook().getName())) {
                    Book book = BookConverter.toBook(bookAuthorDto.getBook());
                    Long bookId = bookDao.add(book);
                    Long authorId = authorDao.add(author);
                    book.setAuthors(Arrays.asList(authorDao.getById(authorId)));
                    bookDao.update(book);
                    author.setBooks(Arrays.asList(bookDao.getById(bookId)));
                    authorDao.update(author);
                }
                authorDao.add(author);
            } else throw new ExistingEntityException();
        } else
            throw new NullAuthorException();
    }

    @Override
    public void addOnlyAuthor(AuthorDto authorDto) {
        authorDao.add(AuthorConverter.toAuthor(authorDto));
    }

    @Override
    public void delete(int id) {
        authorDao.delete(Long.valueOf(id));
    }

/*    @Override
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
    }*/

}
