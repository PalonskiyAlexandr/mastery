package com.palonskiy.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @EmbeddedId
    public BookAuthorId id;

    @ManyToOne
    @MapsId("authorId")

    public Author author;

    @ManyToOne
    @MapsId("bookId")
    public Book book;

    public BookAuthor() {
    }

    public BookAuthor(Author author, Book book) {
        this.author = author;
        this.book = book;
    }

    public BookAuthorId getId() {
        return id;
    }

    public void setId(BookAuthorId id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthor that = (BookAuthor) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(author, that.author) &&
                Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, book);
    }
}
