package com.palonskiy.controllers;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.service.AuthorService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/update-author/{id}")
    public String updateAuthorPage(@PathVariable Long id, Model model) {
        AuthorDto authorDto = authorService.getById(id);
        model.addAttribute("authorDto", authorService.getById(id));
        return "update-author";
    }

    @PostMapping("/update-author")
    public String updateAuthor(@ModelAttribute AuthorDto authorDto) {
        authorService.update(authorDto);
        return "redirect:/";
    }

    @GetMapping("/authors")
    public String getAll(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "authors";
    }

    @PostMapping("/delete-author/{id}")
    public String deleteAuthor(@PathVariable String id) {
        authorService.delete(Long.valueOf(id));
        return "redirect:/authors";
    }

    @PostMapping("/new-author")
    public String newAuthor(@ModelAttribute AuthorDto author) {
        /*author.setId(10L);*/
        authorService.add(author);
        return "redirect:/authors";
    }

    @GetMapping("/new-author")
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
