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

    public AuthorServiceImpl(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    @Override
    public List<AuthorDto> getAll() {
        return AuthorConverter.toDtoList(authorDao.getAll());
    }

    @Override
    public AuthorDto getById(long id) {
        return AuthorConverter.toAuthorDto(authorDao.getById(id));
    }

    @Override
    public void update(AuthorDto authorDto) {
        authorDao.update(AuthorConverter.toAuthor(authorDto));
    }

    @Override
    public List<BookDto> getAuthorBooks(long id) {
        return BookConverter.toDtoList(authorDao.getAuthorBooks(id));
    }

    @Override
    public Author add(AuthorDto authorDto) {
        return authorDao.add(AuthorConverter.toAuthor(authorDto));
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
        List<BookDto> list = BookConverter.toDtoList(authorDao.getByJoinField(obj, fieldName));
        return list;
    }

    @Override
    public boolean checkIfExist(AuthorDto authorDto) {
        return authorDao.checkIfExist(authorDto);
    }
}
