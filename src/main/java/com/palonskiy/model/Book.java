package com.palonskiy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Book {

    @Transient
    static final String NULL_MESSAGE = "Fields can not be empty";
    @Transient
    static final String SIZE_MESSAGE = "Name cannot be less than three letters";
    @Transient
    static final String PATTERN_MESSAGE = "Name has to be in Russian and start with a capital letter";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = NULL_MESSAGE)
    @Size(min = 3, message = SIZE_MESSAGE)
    @Pattern(regexp = "^[А-ЯA-Z][a-zA-Zа-яА-Я ]+", message = PATTERN_MESSAGE)
    private String name;

    @ManyToMany(mappedBy = "books",
            cascade = CascadeType.ALL)
   /* @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)*/
    private List<Author> authors = new ArrayList<>();

    @NotNull(message = NULL_MESSAGE)
    private LocalDate year;

    @NotNull(message = NULL_MESSAGE)
    @Size(min = 3, message = SIZE_MESSAGE)
    @Pattern(regexp = "^[А-ЯA-Z][a-zA-Zа-яА-Я ]+", message = PATTERN_MESSAGE)
    private String publisher;

    public Book() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(name, book.name) &&
                Objects.equals(year, book.year) &&
                Objects.equals(publisher, book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authors, year, publisher);
    }
}
