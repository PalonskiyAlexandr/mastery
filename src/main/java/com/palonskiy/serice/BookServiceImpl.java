package com.palonskiy.serice;

import com.palonskiy.converters.AuthorConverter;
import com.palonskiy.converters.BookConverter;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public BookDto getById(int id) {
        return BookConverter.toBookDto(bookDao.getById(Long.valueOf(id)));
    }

    @Override
    public void update(BookDto bookDto) {
        bookDao.update(BookConverter.toBook(bookDto));
    }

    @Override
    public List<AuthorDto> getBookAuthors(int id) {
        return AuthorConverter.toDtoList(bookDao.getBookAuthors(Long.valueOf(id)));
    }

    @Override
    public void add(BookAuthorDto bookAuthorDto) {
        if (bookAuthorDto.getBook() == null) {
            throw new NullBookException();
        }
        if (bookAuthorDto.getAuthor() == null) {
            new NullAuthorException();
        }
        if (!bookDao.checkIfExist(bookAuthorDto.getBook().getName())) {
            Book book = bookDao.add(BookConverter.toBook(bookAuthorDto.getBook()));
            Author author = authorService.add(bookAuthorDto.getAuthor());
            book.setAuthors(Arrays.asList(author));
            bookDao.update(book);
        } else throw new ExistingEntityException();
    }

    @Override
    public void delete(int id) {
        bookDao.delete(bookDao.getById(Long.valueOf(id)));
    }

    @Override
    public Boolean checkIfExist(String name) {
        return bookDao.checkIfExist(name);
    }

    @Override
    public List<BookDto> getByField(Object obj, String fieldName) {
        List<BookDto> list = BookConverter.toDtoList(bookDao.getByField(obj, fieldName));
        if(list.isEmpty()){
            throw new NoResultException();
        }
        return list;
    }
}
