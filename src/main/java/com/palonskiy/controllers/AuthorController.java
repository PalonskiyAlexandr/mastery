package com.palonskiy.controllers;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.serice.AuthorService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/updateAuthor/{id}")
    public String updateAuthorPage(@PathVariable String id, Model model) {
        model.addAttribute(authorService.getById(Long.valueOf(id)));
        return "updateAuthor";
    }

    @PostMapping("/updateAuthor")
    public String updateAuthor(@ModelAttribute AuthorDto authorDto) {
        authorService.update(authorDto);
        return "redirect:/";
    }

    @GetMapping("/authors")
    public String getAll(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "authors";
    }

    @PostMapping("/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable String id) {
        authorService.delete(Long.valueOf(id));
        return "redirect:/authors";
    }

    @PostMapping("/newAuthor")
    public String newAuthor(@ModelAttribute AuthorDto author) {
        author.setId(1L);
        authorService.add(author);
        return "redirect:/authors";
    }

    @GetMapping("/newAuthor")
    public String newAuthorPage(Model model) {
        model.addAttribute("author", new AuthorDto());
        return "newAuthor";
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
