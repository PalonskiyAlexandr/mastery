package com.palonskiy.controllers;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.serice.BookService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.Arrays;
import java.util.Locale;

@Controller
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getAll(Model model, Locale locale) {
        model.addAttribute("books", bookService.getBookAuthorList());
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "index";
    }

    @GetMapping("/newBook")
    public String newBookPage(Model model) {
        model.addAttribute("bookAuthorDto", new BookDto());
        return "newBook";
    }

    @PostMapping("/newBook")
    public String newBook(Model model, @ModelAttribute BookDto bookDto) {
        bookDto.setId(1l);
        model.addAttribute("author", new AuthorDto());
        return "newBookAuthor";
    }

    @PostMapping("/newBookAuthor")
    public String newBookAuthor(@ModelAttribute BookDto bookDto, @ModelAttribute AuthorDto authorDto) {
        authorDto.setId(1l);
        BookAuthorDto bookAuthorDto = new BookAuthorDto();
        bookAuthorDto.setBook(bookDto);
        bookAuthorDto.setAuthor(Arrays.asList(authorDto));
        bookService.add(bookAuthorDto);
        return "redirect:/";
    }

    @PostMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable String id) {
        bookService.delete(Long.valueOf(id));
        return "redirect:/";
    }

    @GetMapping("/updateBook/{id}")
    public String updateBookPage(@PathVariable String id, Model model) {
        model.addAttribute(bookService.getById(Long.valueOf(id)));
        return "updateBook";
    }

    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute BookDto bookDto) {
        bookService.update(bookDto);
        return "redirect:/";
    }

    /*@GetMapping("/book")
    public ResponseEntity<List<BookDto>> getAll() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("bookAuthors/{id}")
    public ResponseEntity<List<AuthorDto>> getBookAuthors(@PathVariable int id) {
        return new ResponseEntity<>(bookService.getBookAuthors(id), HttpStatus.OK);
    }

    @PostMapping("/book")
    public ResponseEntity<?> newBook(@RequestBody BookAuthorDto bookAuthorDto) {
        bookService.add(bookAuthorDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/book")
    public ResponseEntity<?> deleteBook(@RequestParam int id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/book")
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto) {
        bookService.update(bookDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable int id) {
        return new ResponseEntity<>(bookService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/bookByName")
    public ResponseEntity<List<BookDto>> getBookByName(@RequestParam String name) {
        return new ResponseEntity<>(bookService.getByField(name, "name"), HttpStatus.OK);
    }

    //LocalDate???
    @GetMapping("/bookByYear")
    public ResponseEntity<List<BookDto>> getBookByYear(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate year){
        return new ResponseEntity<>(bookService.getByField(year, "year"), HttpStatus.OK);
    }

    @GetMapping("/bookByPublisher")
    public ResponseEntity<List<BookDto>> getBookByPublisher(@RequestParam String publisher) {
        return new ResponseEntity<>(bookService.getByField(publisher, "publisher"), HttpStatus.OK);
    }*/

}
