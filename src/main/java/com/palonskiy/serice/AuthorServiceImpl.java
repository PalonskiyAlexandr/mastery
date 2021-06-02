package com.palonskiy.serice;

import com.palonskiy.converters.AuthorConverter;
import com.palonskiy.converters.BookConverter;
import com.palonskiy.dao.AuthorDao;
import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Author;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
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
        authorDao.delete(authorDao.getById(id));
    }

    @Override
    public List<BookDto> getByJoinField(Object obj, String fieldName) {
        List<BookDto> list = BookConverter.toDtoList(authorDao.getByJoinField(obj, fieldName));
        return list;
    }

}
