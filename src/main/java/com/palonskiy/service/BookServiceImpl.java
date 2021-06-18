package com.palonskiy.service;

import com.palonskiy.converters.AuthorConverter;
import com.palonskiy.converters.BookConverter;
import com.palonskiy.dao.BookDao;
import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.exceptions.ExistingEntityException;
import com.palonskiy.exceptions.NullAuthorException;
import com.palonskiy.exceptions.NullBookException;
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private BookDao bookDao;
    private AuthorService authorService;

    private AuthorConverter authorConverter;
    private BookConverter bookConverter;


    public BookServiceImpl(BookDao bookDao, AuthorService authorService, AuthorConverter authorConverter, BookConverter bookConverter) {
        this.bookDao = bookDao;
        this.authorService = authorService;

        this.authorConverter = authorConverter;
        this.bookConverter = bookConverter;
    }

    @Override
    public List<BookDto> getAll() {
        return bookConverter.toDtoList(bookDao.getAll());
    }

    @Override
    public BookDto getById(long bookId) {
        return bookConverter.toBookDto(bookDao.getById(bookId));
    }

    @Override
    public void update(BookDto bookDto) {
        Book book = bookDao.getById(bookDto.getId());
        BeanUtils.copyProperties(bookDto, book);
        bookDao.update(book);
    }

    @Override
    public void updateWithAuthor(BookAuthorDto bookAuthorDto) {
        Book book = bookDao.getById(bookAuthorDto.getBook().getId());
        BeanUtils.copyProperties(bookAuthorDto.getBook(), book);
        book.setAuthors(authorConverter.toList(bookAuthorDto.getAuthors()));
        bookDao.update(book);
    }

    @Override
    public List<AuthorDto> getBookAuthors(long bookId) {
        return authorConverter.toDtoList(bookDao.getBookAuthors(bookId));
    }


    public List<BookAuthorDto> getBookAuthorList() {
        List<BookAuthorDto> list = new ArrayList<>();
        for (BookDto book : getAll()) {
            BookAuthorDto bookAuthorDto = new BookAuthorDto();
            bookAuthorDto.setBook(book);
            long id = book.getId();
            bookAuthorDto.setAuthor(getBookAuthors(id));
            list.add(bookAuthorDto);
        }
        return list;
    }

    @Override
    public void add(BookAuthorDto bookAuthorDto) {
        if (bookAuthorDto.getBook() == null) {
            throw new NullBookException();
        }
        if (bookAuthorDto.getAuthors() == null) {
            new NullAuthorException();
        }
        if (!bookDao.checkIfExist(bookAuthorDto.getBook().getName())) {
            Book book = bookDao.add(bookConverter.toBook(bookAuthorDto.getBook()));
            List<Author> authors = new ArrayList<>();
            for (AuthorDto author : bookAuthorDto.getAuthors()) {
                long id;
                if (!authorService.checkIfExist(author)) {
                    id = authorService.add(author).getId();
                }else {
                    id = author.getId();
                }
                authors.add(authorConverter.toAuthor(authorService.getById(id)));
            }
            book.setAuthors(authors);
            bookDao.update(book);
        } else throw new ExistingEntityException();
    }

    @Override
    public void delete(long bookId) {
        bookDao.delete(bookDao.getById(bookId));
    }

    @Override
    public boolean checkIfExist(String name) {
        return bookDao.checkIfExist(name);
    }

    @Override
    public List<BookDto> getByField(Object obj, String fieldName) {
        List<BookDto> list = bookConverter.toDtoList(bookDao.getByField(obj, fieldName));
        return list;
    }

    @Override
    public BookAuthorDto createBookAuthorDto(BookDto bookDto, AuthorDto authorDto) {
        return new BookAuthorDto(Arrays.asList(authorDto), bookDto);
    }

    @Override
    public BookAuthorDto createBookAuthorDto(BookDto bookDto, long authorDtoId) {
        return new BookAuthorDto(Arrays.asList(authorService.getById(authorDtoId)), bookDto);
    }

    @Override
    public BookAuthorDto createBookAuthorDto(long bookDtoId, long authorDtoId) {
        List<AuthorDto> authors = getBookAuthors(bookDtoId);
        authors.add(authorService.getById(authorDtoId));
        return new BookAuthorDto(authors, getById(bookDtoId));
    }
}
