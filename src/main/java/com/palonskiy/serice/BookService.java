package com.palonskiy.serice;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Book;

import java.util.List;

public interface BookService {
    List<BookDto> get();

    List<AuthorDto> getBookAuthors(int id);

    Long addOnlyBook(BookDto bookDto);

    void add(BookAuthorDto bookAuthorDto);
    void delete(int id);

    Boolean checkIfExist(String name);

    BookDto getById(int id);

    void update(BookDto bookDto);

/*    List<BookAuthorDto> sortByYear(int year);

    List<BookAuthorDto> sortByName(String name);

    List<BookAuthorDto> sortByPublisher(String name);*/
}
