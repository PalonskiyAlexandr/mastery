package com.palonskiy.controllers;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.service.AuthorService;
import com.palonskiy.service.BookService;
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

    @GetMapping("/")
    public String getAll(Model model, Locale locale) {
        model.addAttribute("books", bookService.getBookAuthorList());
        return "index";
    }

    @GetMapping("/new-book")
    public String newBookPage(Model model) {
        model.addAttribute("bookDto", new BookDto());
        model.addAttribute("index", new String());
        return "new-book";
    }


    @PostMapping("/new-book")
    public String newBook(Model model, HttpSession session, @ModelAttribute BookDto bookDto, @RequestParam(value = "action", required = true) String action) {
            /*bookDto.setId(1l);*/
            session.setAttribute("bookDto", bookDto);
        if (action.equals("1")) {
            model.addAttribute("author", new AuthorDto());
            return "new-book-author";
        } else  {
            model.addAttribute("authors", authorService.getAll());
            return "new-book-author-assign";
        }
    }

    @PostMapping("/new-book-author")
    public String newBookAuthor(HttpSession session, @ModelAttribute AuthorDto authorDto) {
        /*authorDto.setId(1l);*/
        BookDto bookDto = (BookDto) session.getAttribute("bookDto");
        BookAuthorDto bookAuthorDto = new BookAuthorDto();
        bookAuthorDto.setBook(bookDto);
        bookAuthorDto.setAuthor(Arrays.asList(authorDto));
        bookService.add(bookAuthorDto);
        return "redirect:/";
    }

    @PostMapping("/assign-author/{id}")
    public String assignAuthor (HttpSession session, @PathVariable String id) {
        BookDto bookDto = (BookDto) session.getAttribute("bookDto");
        BookAuthorDto bookAuthorDto = new BookAuthorDto();
        bookAuthorDto.setBook(bookDto);
        bookAuthorDto.setAuthor(Arrays.asList(authorService.getById(Long.valueOf(id))));
        bookService.add(bookAuthorDto);
        return "redirect:/";
    }


    @PostMapping("/old-assign-author/{authorId}")
    public String oldAssignAuthor (HttpSession session, @PathVariable String authorId) {
        String bookId = session.getAttribute("bookId").toString();
        List<AuthorDto> authors = bookService.getBookAuthors(Long.valueOf(bookId));
        authors.add(authorService.getById(Long.valueOf(authorId)));
        BookAuthorDto bookAuthorDto = new BookAuthorDto();
        bookAuthorDto.setBook(bookService.getById(Long.valueOf(bookId)));
        bookAuthorDto.setAuthor(authors);
        bookService.updateWithAuthor(bookAuthorDto);
        return "redirect:/";
    }

    @PostMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable String id) {
        bookService.delete(Long.valueOf(id));
        return "redirect:/";
    }

    @GetMapping("/update-book/{id}")
    public String updateBookPage(@PathVariable String id, Model model, HttpSession session) {
        model.addAttribute(bookService.getById(Long.valueOf(id)));
        session.setAttribute("bookId", id);
        return "update-book";
    }

    @PostMapping("/update-book")
    public String updateBook(Model model, @ModelAttribute BookDto bookDto, @RequestParam(value = "action", required = true) String action) {
        if (action.equals("1")) {
            bookService.update(bookDto);
            return "redirect:/";
        } else  {
            List<AuthorDto> authors = authorService.getAll();
            List<AuthorDto> exAuthors = bookService.getBookAuthors(bookDto.getId());
            for (int i=0; i<authors.size(); i++){
                for(int j = 0; j<exAuthors.size(); j++){
                    if(authors.get(i).getFirstName().equals(exAuthors.get(j).getFirstName()) && authors.get(i).getSecondName().equals(exAuthors.get(j).getSecondName())){
                        authors.remove(i);
                    }
                }
            }
            authors.remove(exAuthors);
            model.addAttribute("authors", authors);
            return "old-book-author-assign";
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
