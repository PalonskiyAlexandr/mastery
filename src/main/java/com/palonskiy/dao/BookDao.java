package com.palonskiy.dao;

import com.palonskiy.model.Author;
import com.palonskiy.model.Book;

import java.util.List;

public interface BookDao extends CrudDao<Book> {

    List<Author> getBookAuthors(long BookId);

    boolean checkIfBookExist(String name);


}
