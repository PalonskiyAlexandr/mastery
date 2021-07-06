package com.palonskiy.converters;

import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Book;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter {

    public List<BookDto> toDtoList(List<Book> books) {
        return books.stream()
                .map(this::toBookDto)
                .collect(Collectors.toList());
    }

    public Book toBook(BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        return book;
    }

    public BookDto toBookDto(Book book) {
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        return bookDto;
    }
}
