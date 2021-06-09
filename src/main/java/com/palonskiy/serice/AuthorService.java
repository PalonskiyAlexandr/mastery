package com.palonskiy.serice;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Author;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();

    List<BookDto> getAuthorBooks(long id);

    Author add(AuthorDto authorDto);

    void delete(long id);

    AuthorDto getById(long id);

    void update(AuthorDto authorDto);

    List<BookDto> getByJoinField(Object obj, String fieldName);

    boolean checkIfExist(AuthorDto authorDto);
}
