package com.palonskiy.service;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Author;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();

    List<BookDto> getAuthorBooks(long authorId);

    Author add(AuthorDto authorDto);

    void delete(long authorId);

    AuthorDto getById(long authorId);

    void update(AuthorDto authorDto);

    List<BookDto> getByJoinField(Object obj, String fieldName);

    boolean checkIfExist(AuthorDto authorDto);
}
