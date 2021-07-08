package com.palonskiy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "Author")
public class Author {

    @Transient
    static final String NULL_MESSAGE = "Fields can not be empty";
    @Transient
    static final String SIZE_MESSAGE = "Name cannot be less than three letters";
    @Transient
    static final String PATTERN_MESSAGE = "Name has to be in Russian and start with a capital letter";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = NULL_MESSAGE)
    @Size(min = 3, message = SIZE_MESSAGE)
    /*    @Pattern(regexp = "^[A-Z][a-z]+", message = PATTERN_MESSAGE)*/
    private String firstName;

    @NotNull(message = NULL_MESSAGE)
    @Size(min = 3, message = SIZE_MESSAGE)
    /*    @Pattern(regexp = "^[A-Z][a-z]+", message = PATTERN_MESSAGE)*/
    private String secondName;

    /*    @NotNull(message = NULL_MESSAGE)*/
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany(mappedBy = "authors",
            cascade = CascadeType.PERSIST)
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
                Objects.equals(firstName, author.firstName) &&
                Objects.equals(secondName, author.secondName) &&
                Objects.equals(birthday, author.birthday) &&
                gender == author.gender &&
                Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, birthday, gender, books);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", birthday=" + birthday +
                ", sex=" + gender +
                ", books=" + books +
                '}';
    }
}
