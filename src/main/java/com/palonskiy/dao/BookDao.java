package com.palonskiy.dao;

import com.palonskiy.model.Author;
import com.palonskiy.model.Book;

import java.util.List;

public interface BookDao extends CrudDao<Book>{

    Author getBookAuthor(Long BookId);

    boolean checkIfExist(String name);


}
