package com.palonskiy.dao;

import com.palonskiy.model.Author;
import com.palonskiy.model.Book;

import java.util.List;

public interface BookDao extends CrudDao<Book>{

    List<Author> getBookAuthors(Long BookId);

    boolean checkIfExist(String name);


}
