package com.palonskiy.dao;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;

import java.util.List;

public interface AuthorDao extends CrudDao<Author> {

    List<Book> getAuthorBooks(Long authorId);

    boolean checkIfExist(AuthorDto authorDto);

    List<Book> getByJoinField(Object obj, String fieldName);
}
