package com.palonskiy.controllers;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Action;
import com.palonskiy.service.AuthorService;
import com.palonskiy.service.BookService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
public class BookController {

    private BookService bookService;
    private AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/get-book-info/{bookId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getBookInfo(@PathVariable long bookId, Model model, HttpSession session) {
        model.addAttribute(bookService.getById(bookId));
        session.setAttribute("bookId", bookId);
        return "book-info";
    }

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("books", bookService.getBookAuthorList());
        return "index";
    }

    @GetMapping("/admin/books")
    @PreAuthorize("hasRole('ADMIN')")
    public String getListBooks(Model model) {
        model.addAttribute("books", bookService.getBookAuthorList());
        return "admin-books";
    }

    @GetMapping("/admin/new-book")
    @PreAuthorize("hasRole('ADMIN')")
    public String newBookPage(Model model) {
        model.addAttribute("bookDto", new BookDto());
        return "new-book";
    }


    @PostMapping("/admin/new-book")
    @PreAuthorize("hasRole('ADMIN')")
    public String newBook(Model model, HttpSession session, @ModelAttribute BookDto bookDto,
                          @RequestParam(value = "action") Action action) {
            session.setAttribute("bookDto", bookDto);
        if (Action.CREATE_AUTHOR.equals(action)) {
            model.addAttribute("author", new AuthorDto());
            return "new-book-author";
        } else  {
            model.addAttribute("authors", authorService.getAll());
            return "new-book-author-assign";
        }
    }

    @PostMapping("/admin/new-book-author")
    @PreAuthorize("hasRole('ADMIN')")
    public String newBookAuthor(HttpSession session, @ModelAttribute AuthorDto authorDto) {
        BookDto bookDto = (BookDto) session.getAttribute("bookDto");
        bookService.add(bookService.createBookAuthorDto(bookDto, authorDto));
        return "redirect:/admin/books";
    }

    @PostMapping("/admin/assign-author/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String assignAuthor (HttpSession session, @PathVariable long authorId) {
        BookDto bookDto = (BookDto) session.getAttribute("bookDto");
        bookService.add(bookService.createBookAuthorDto(bookDto, authorId));
        return "redirect:/admin/books";
    }


    @PostMapping("/admin/old-assign-author/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String oldAssignAuthor (HttpSession session, @PathVariable long authorId) {
        String bookId = session.getAttribute("bookId").toString();
        bookService.updateWithAuthor(bookService.createBookAuthorDto(Long.valueOf(bookId), authorId));
        return "redirect:/";
    }

    @PostMapping("/admin/delete-book/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable long bookId) {
        bookService.delete(bookId);
        return "redirect:/admin/books";
    }

    @GetMapping("/admin/update-book/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateBookPage(@PathVariable long bookId, Model model, HttpSession session) {
        model.addAttribute(bookService.getById(bookId));
        session.setAttribute("bookId", bookId);
        return "update-book";
    }

    @PostMapping("/admin/update-book")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateBook(Model model, @ModelAttribute BookDto bookDto,
                             @RequestParam(value = "action") Action action) {
        if (Action.ASSIGN_AUTHOR.equals(action)) {
            model.addAttribute("authors", authorService.getAuthorExceptAuthors(bookDto));
            return "old-book-author-assign";
        } else  {
            bookService.update(bookDto);
            return "redirect:/admin/books";
        }

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
