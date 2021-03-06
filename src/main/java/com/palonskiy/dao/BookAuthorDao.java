package com.palonskiy.dao;

import com.palonskiy.model.BookAuthor;

import java.util.List;

public interface BookAuthorDao {

    void add(BookAuthor bookAuthor);

    List<BookAuthor> get();

    List<BookAuthor> findBooksByAuthor(Long authorId);

    List<BookAuthor> findAuthorsByBook(Long bookId);

}
