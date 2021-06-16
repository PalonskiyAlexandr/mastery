package com.palonskiy.converters;

import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Book;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookConverter {

    public List<BookDto> toDtoList(List<Book> books) {
        List<BookDto> booksDto = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            booksDto.add(bookDto);
        }
        return booksDto;
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
