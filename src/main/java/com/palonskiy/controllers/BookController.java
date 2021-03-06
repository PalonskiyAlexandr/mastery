package com.palonskiy.controllers;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.serice.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/Mastery_jar/book")
    public ResponseEntity<List<BookDto>> get() {
        return new ResponseEntity<>(bookService.get(), HttpStatus.OK);
    }

    @GetMapping("/Mastery_jar/bookAuthors/{id}")
    public ResponseEntity<List<AuthorDto>> getBookAuthors(@PathVariable int id) {
        return new ResponseEntity<>(bookService.getBookAuthors(id), HttpStatus.OK);
    }

    @PostMapping("/Mastery_jar/book")
    public ResponseEntity<?> newBook(@RequestBody BookAuthorDto bookAuthorDto) {
        bookService.add(bookAuthorDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/Mastery_jar/book")
    public ResponseEntity<?> deleteBook(@RequestParam int id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/Mastery_jar/book")
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto) {
        bookService.update(bookDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/Mastery_jar/book/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable int id) {
        return new ResponseEntity<>(bookService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/Mastery_jar/bookByName")
    public ResponseEntity<List<BookAuthorDto>> getBookByName(@RequestParam String name) {
        return new ResponseEntity<>(bookService.sortByName(name), HttpStatus.OK);
    }

    @GetMapping("/Mastery_jar/bookByYear")
    public ResponseEntity<List<BookAuthorDto>> getBookByYear(@RequestParam int year) {
        return new ResponseEntity<>(bookService.sortByYear(year), HttpStatus.OK);
    }

    @GetMapping("/Mastery_jar/bookByPublisher")
    public ResponseEntity<List<BookAuthorDto>> getBookByPublisher(@RequestParam String name) {
        return new ResponseEntity<>(bookService.sortByPublisher(name), HttpStatus.OK);
    }

}
