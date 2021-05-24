package com.palonskiy.serice;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    List<AuthorDto> getBookAuthors(int id);

    void add(BookAuthorDto bookAuthorDto);

    void delete(int id);

    Boolean checkIfExist(String name);

    BookDto getById(int id);

    void update(BookDto bookDto);

    List<BookDto> getByField(Object obj, String fieldName);
}
