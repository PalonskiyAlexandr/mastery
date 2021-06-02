package com.palonskiy.dao;

import com.palonskiy.model.Author;
import com.palonskiy.model.Book;

public interface BookDao extends CrudDao<Book>{

    Author getBookAuthor(long BookId);

    boolean checkIfExist(String name);


}
