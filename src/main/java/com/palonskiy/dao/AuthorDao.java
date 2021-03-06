package com.palonskiy.dao;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;

import java.util.List;

public interface AuthorDao extends CrudDao<Author> {

    List<Book> getAuthorBooks(long authorId);

    boolean checkIfAuthorExist(AuthorDto authorDto);

    List<Book> getByJoinField(Object obj, String fieldName);

    List<Author> getAuthorExceptAuthors(BookDto bookDto);
}
