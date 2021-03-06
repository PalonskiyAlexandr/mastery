package com.palonskiy.converters;

import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Book;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class BookConverter {

    private BookConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static List<BookDto> toDtoList(List<Book> books) {
        List<BookDto> booksDto = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            booksDto.add(bookDto);
        }
        return booksDto;
    }

    public static Book toBook(BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        return book;
    }

    public static BookDto toBookDto(Book book) {
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        return bookDto;
    }
}
