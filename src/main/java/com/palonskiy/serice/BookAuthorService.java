package com.palonskiy.serice;

import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.model.BookAuthor;

import java.util.List;

public interface BookAuthorService {

    void add(BookAuthorDto bookAuthorDto, Long bookId, Long authorId);

    List<BookAuthorDto> get();

    List<Long> findBooksByAuthor(Long authorId);

    List<BookAuthor> findAuthorsByBook(Long bookId);
}
