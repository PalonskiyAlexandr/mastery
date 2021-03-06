package com.palonskiy.serice;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> get();

    List<BookDto> getAuthorBooks(int id);

    void add(BookAuthorDto bookAuthorDto);

    Long getId();

    void addOnlyAuthor(AuthorDto authorDto);

    void delete(int id);

    AuthorDto getById(int id);

    void update(AuthorDto authorDto);

    List<BookAuthorDto> sortByBirthday(int year);

    List<BookAuthorDto> sortBySex(String sex);

    List<BookAuthorDto> sortByName(String name);

    List<BookAuthorDto> sortBySecondName(String name);
}
