package com.palonskiy.service;

import com.palonskiy.converters.AuthorConverter;
import com.palonskiy.converters.BookConverter;
import com.palonskiy.dao.AuthorDao;
import com.palonskiy.dao.BookDao;
import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao;
    private BookDao bookDao;

    private AuthorConverter authorConverter;
    private BookConverter bookConverter;

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
    public AuthorDto getById(long id) {
        return authorConverter.toAuthorDto(authorDao.getById(id));
    }

    @Override
    public void update(AuthorDto authorDto) {
        authorDao.update(authorConverter.toAuthor(authorDto));
    }

    @Override
    public List<BookDto> getAuthorBooks(long id) {
        return bookConverter.toDtoList(authorDao.getAuthorBooks(id));
    }

    @Override
    public Author add(AuthorDto authorDto) {
        return authorDao.add(authorConverter.toAuthor(authorDto));
    }

    @Override
    public void delete(long id) {
        List<Book> books = authorDao.getAuthorBooks(id);
        Author author = authorDao.getById(id);
        for (Book book:books) {
            List<Author> authors = bookDao.getBookAuthors(book.getId());
            if (authors.size()==1)
            {
                bookDao.delete(book);
            }else{
                authors.remove(author);
                book.setAuthors(authors);
                bookDao.update(book);
            }
        }
        authorDao.delete(author);
    }

    @Override
    public List<BookDto> getByJoinField(Object obj, String fieldName) {
        List<BookDto> list = bookConverter.toDtoList(authorDao.getByJoinField(obj, fieldName));
        return list;
    }

    @Override
    public boolean checkIfExist(AuthorDto authorDto) {
        return authorDao.checkIfExist(authorDto);
    }
}
