package com.palonskiy.dao;

import com.palonskiy.model.Author;
import com.palonskiy.model.BookAuthor;

import java.util.List;

public interface AuthorDao {
    List<Author> get();

    List<BookAuthor> getAuthorBooks();

    Author getById(Long id);

    void add(Author author);

    Long getId();

    void delete(Long id);

    Author checkIfExist(String secondName);

    void update(Author author);
}
