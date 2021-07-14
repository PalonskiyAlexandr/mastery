package com.palonskiy.controllers;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.model.Action;
import com.palonskiy.service.AuthorService;
import com.palonskiy.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }


    @GetMapping("/books/{bookId}")
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
        } else {
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
    public String assignAuthor(HttpSession session, @PathVariable long authorId) {
        BookDto bookDto = (BookDto) session.getAttribute("bookDto");
        bookService.add(bookService.createBookAuthorDto(bookDto, authorId));
        return "redirect:/admin/books";
    }


    @PostMapping("/admin/old-assign-author/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String oldAssignAuthor(HttpSession session, @PathVariable long authorId) {
        String bookId = session.getAttribute("bookId").toString();
        bookService.updateWithAuthor(bookService.createBookAuthorDto(Long.parseLong(bookId), authorId));
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
        } else {
            bookService.update(bookDto);
            return "redirect:/admin/books";
        }

    }
}
