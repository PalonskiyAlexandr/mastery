package com.palonskiy.dao;

import com.palonskiy.model.Book;
import com.palonskiy.model.BookAuthor;

import java.util.List;

public interface BookDao {
    List<Book> get();

    List<BookAuthor> getBookAuthors();

    Book getById(Long id);

    void add(Book book);

    Long getId();

    void delete(Long id);

    Book checkIfExist(String name);

    void update(Book book);

}
