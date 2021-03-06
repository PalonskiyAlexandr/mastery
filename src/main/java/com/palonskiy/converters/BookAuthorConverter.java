package com.palonskiy.converters;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Author;
import com.palonskiy.model.Book;
import com.palonskiy.model.BookAuthor;
import com.palonskiy.model.BookAuthorId;

import java.util.ArrayList;
import java.util.List;

public class BookAuthorConverter {

    private BookAuthorConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static BookAuthor toBookAuthor(BookAuthorDto bookAuthorDto, Long authorId, Long bookId) {
        BookAuthor bookAuthor = new BookAuthor();
        Author author = AuthorConverter.toAuthor(bookAuthorDto.getAuthor());
        author.setId(authorId);
        bookAuthor.setAuthor(author);
        Book book = BookConverter.toBook(bookAuthorDto.getBook());
        book.setId(bookId);
        bookAuthor.setBook(book);
        bookAuthor.setId(new BookAuthorId(authorId, bookId));
        return bookAuthor;
    }

    public static BookAuthorDto toBookAuthorDto(BookAuthor bookAuthor) {
        AuthorDto author = AuthorConverter.toAuthorDto(bookAuthor.getAuthor());
        BookDto book = BookConverter.toBookDto(bookAuthor.getBook());
        BookAuthorDto bookAuthorDto = new BookAuthorDto();
        bookAuthorDto.setAuthor(author);
        bookAuthorDto.setBook(book);
        return bookAuthorDto;
    }

    public static List<BookAuthorDto> toBookAuthorDtoList(List<BookAuthor> bookAuthors) {
        List<BookAuthorDto> bookAuthorDtos = new ArrayList<>();
        for (BookAuthor bookAuthor : bookAuthors) {
            bookAuthorDtos.add(BookAuthorConverter.toBookAuthorDto(bookAuthor));
        }
        return bookAuthorDtos;
    }
}
