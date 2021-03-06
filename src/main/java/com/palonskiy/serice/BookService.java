package com.palonskiy.serice;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Book;

import java.util.List;

public interface BookService {
    List<BookDto> get();

    List<AuthorDto> getBookAuthors(int id);

    void addOnlyBook(BookDto bookDto);

    Long getId();

    void add(BookAuthorDto bookAuthorDto);

    void delete(int id);

    Book checkIfExist(String name);

    BookDto getById(int id);

    void update(BookDto bookDto);

    List<BookAuthorDto> sortByYear(int year);

    List<BookAuthorDto> sortByName(String name);

    List<BookAuthorDto> sortByPublisher(String name);
}
