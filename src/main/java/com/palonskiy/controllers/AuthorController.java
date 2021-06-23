package com.palonskiy.controllers;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.service.AuthorService;


import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/get-author-info/{authorId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getAuthorInfo(@PathVariable long authorId, Model model) {
        model.addAttribute("authorDto", authorService.getById(authorId));
        return "author-info";
    }

    @GetMapping("/admin/update-author/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateAuthorPage(@PathVariable long authorId, Model model) {
        model.addAttribute("authorDto", authorService.getById(authorId));
        return "update-author";
    }

    @PostMapping("/admin/update-author")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateAuthor(@ModelAttribute AuthorDto authorDto) {
        authorService.update(authorDto);
        return "redirect:/admin/authors";
    }

    @GetMapping("/authors")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getAll(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "authors";
    }

    @GetMapping("/admin/authors")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAuthorList(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "admin-authors";
    }

    @PostMapping("/admin/delete-author/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAuthor(@PathVariable long authorId) {
        authorService.delete(authorId);
        return "redirect:/admin/authors";
    }

    @PostMapping("/admin/new-author")
    @PreAuthorize("hasRole('ADMIN')")
    public String newAuthor(@ModelAttribute AuthorDto author) {
        authorService.add(author);
        return "redirect:/admin/authors";
    }

    @GetMapping("/admin/new-author")
    @PreAuthorize("hasRole('ADMIN')")
    public String newAuthorPage(Model model) {
        model.addAttribute("author", new AuthorDto());
        return "new-author";
    }

    /*@GetMapping("/author")
    public ResponseEntity<List<AuthorDto>> getAll() {
        return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable int id) {
        return new ResponseEntity<>(authorService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/author")
    public ResponseEntity<?> updateAuthor(@RequestBody AuthorDto authorDto) {
        authorService.update(authorDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/authorBooks/{id}")
    public ResponseEntity<List<BookDto>> getAuthorBooks(@PathVariable int id) {
        return new ResponseEntity<>(authorService.getAuthorBooks(id), HttpStatus.OK);
    }

    @PostMapping("/author")
    public ResponseEntity<?> newAuthor(@RequestBody AuthorDto AuthorDto) {
        authorService.add(AuthorDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/author")
    public ResponseEntity<?> deleteAuthor(@RequestParam int id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

/*    @GetMapping("/bookByAuthorName")
    public ResponseEntity<List<BookDto>> getBookByAuthorName(@RequestParam String name) {
        return new ResponseEntity<>(authorService.getByJoinField(name, "firstName"), HttpStatus.OK);
    }
//LocalDate???
    @GetMapping("/bookByAuthorBirthday")
    public ResponseEntity<List<BookDto>> getBookByAuthorBirthday(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(authorService.getByJoinField(date, "birthday"), HttpStatus.OK);
    }

    @GetMapping("/bookByAuthorSecondName")
    public ResponseEntity<List<BookDto>> getBookByAuthorSecondName(@RequestParam String secondName) {
        return new ResponseEntity<>(authorService.getByJoinField(secondName, "secondName"), HttpStatus.OK);
    }

    @GetMapping("/bookByAuthorSex")
    public ResponseEntity<List<BookDto>> getBookByAuthorSex(@RequestParam String sex) {
        return new ResponseEntity<>(authorService.getByJoinField(sex, "sex"), HttpStatus.OK);
    }*/
}
