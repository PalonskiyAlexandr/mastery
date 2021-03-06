package com.palonskiy.dto;

public class BookAuthorDto {

    private AuthorDto author;
    private BookDto book;

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }
}
