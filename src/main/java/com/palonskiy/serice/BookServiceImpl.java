package com.palonskiy.serice;

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

    public BookServiceImpl(BookDao bookDao, AuthorService authorService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
    }

    @Override
    public List<BookDto> getAll() {
        return BookConverter.toDtoList(bookDao.getAll());
    }

    @Override
    public BookDto getById(long id) {
        return BookConverter.toBookDto(bookDao.getById(id));
    }

    @Override
    public void update(BookDto bookDto) {
        Book book = bookDao.getById(bookDto.getId());
        BeanUtils.copyProperties(bookDto, book);
        bookDao.update(book);
    }

    @Override
    public List<AuthorDto> getBookAuthors(long id) {
        return AuthorConverter.toDtoList(bookDao.getBookAuthors(id));
    }


    public List<BookAuthorDto> getBookAuthorList(){
        List<BookAuthorDto> list = new ArrayList<>();
        for (BookDto book:getAll()) {
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
            Book book = bookDao.add(BookConverter.toBook(bookAuthorDto.getBook()));
            List<Author> authors = new ArrayList<>();
            for (AuthorDto author:bookAuthorDto.getAuthors()) {
                authors.add(authorService.add(author));
            }
            book.setAuthors(authors);
            bookDao.update(book);
        } else throw new ExistingEntityException();
    }

    @Override
    public void delete(long id) {
        bookDao.delete(bookDao.getById(id));
    }

    @Override
    public boolean checkIfExist(String name) {
        return bookDao.checkIfExist(name);
    }

    @Override
    public List<BookDto> getByField(Object obj, String fieldName) {
        List<BookDto> list = BookConverter.toDtoList(bookDao.getByField(obj, fieldName));
        return list;
    }
}
