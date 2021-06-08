package com.palonskiy.dto;

import java.util.List;

public class BookAuthorDto {

    private List<AuthorDto> authors;
    private BookDto book;

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthor(List<AuthorDto> authors) {
        this.authors = authors;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }
}
