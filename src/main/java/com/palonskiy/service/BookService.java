package com.palonskiy.service;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    List<AuthorDto> getBookAuthors(long id);

    void add(BookAuthorDto bookAuthorDto);

    void updateWithAuthor(BookAuthorDto bookAuthorDto);

    void delete(long id);

    boolean checkIfExist(String name);

    BookDto getById(long id);

    void update(BookDto bookDto);

    List<BookDto> getByField(Object obj, String fieldName);

    List<BookAuthorDto> getBookAuthorList();
}
