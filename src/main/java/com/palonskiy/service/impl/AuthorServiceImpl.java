package com.palonskiy.service.impl;

import com.palonskiy.converters.AuthorConverter;
import com.palonskiy.converters.BookConverter;
import com.palonskiy.dao.AuthorDao;
import com.palonskiy.dao.BookDao;
import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import com.palonskiy.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final BookDao bookDao;

    private final AuthorConverter authorConverter;
    private final BookConverter bookConverter;

    public AuthorServiceImpl(AuthorDao authorDao, BookDao bookDao, AuthorConverter authorConverter, BookConverter bookConverter) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;

        this.authorConverter = authorConverter;
        this.bookConverter = bookConverter;
    }

    @Override
    public List<AuthorDto> getAll() {
        return authorConverter.toDtoList(authorDao.getAll());
    }

    @Override
    public AuthorDto getById(long authorId) {
        return authorConverter.toAuthorDto(authorDao.getById(authorId));
    }

    @Override
    public void update(AuthorDto authorDto) {
        authorDao.update(authorConverter.toAuthor(authorDto));
    }

    @Override
    public List<BookDto> getAuthorBooks(long authorId) {
        return bookConverter.toDtoList(authorDao.getAuthorBooks(authorId));
    }

    @Override
    public Author add(AuthorDto authorDto) {
        return authorDao.add(authorConverter.toAuthor(authorDto));
    }

    @Override
    public void delete(long authorId) {
        List<Book> books = authorDao.getAuthorBooks(authorId);
        Author author = authorDao.getById(authorId);
        for (Book book : books) {
            List<Author> authors = book.getAuthors();
            if (authors.size() == 1) {
                bookDao.delete(book);
            } else {
                authors.remove(author);
                bookDao.update(book);
            }
        }
        authorDao.delete(author);
    }

    @Override
    public List<BookDto> getByJoinField(Object obj, String fieldName) {
        return bookConverter.toDtoList(authorDao.getByJoinField(obj, fieldName));
    }

    @Override
    public boolean checkIfAuthorExist(AuthorDto authorDto) {
        return authorDao.checkIfAuthorExist(authorDto);
    }

    @Override
    public List<AuthorDto> getAuthorExceptAuthors(BookDto bookDto) {
        return authorConverter.toDtoList(authorDao.getAuthorExceptAuthors(bookDto));
    }
}
