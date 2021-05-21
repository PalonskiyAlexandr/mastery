package com.palonskiy.controllers;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.dto.BookAuthorDto;
import com.palonskiy.dto.BookDto;
import com.palonskiy.serice.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {
    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/author")
    public ResponseEntity<List<AuthorDto>> get() {
        return new ResponseEntity<>(authorService.get(), HttpStatus.OK);
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
    public ResponseEntity<?> newAuthor(@RequestBody BookAuthorDto bookAuthorDto) {
        authorService.add(bookAuthorDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/author")
    public ResponseEntity<?> deleteAuthor(@RequestParam int id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

/*    @GetMapping("/bookByAuthorName")
    public ResponseEntity<List<BookAuthorDto>> getBookByAuthorName(@RequestParam String name) {
        return new ResponseEntity<>(authorService.sortByName(name), HttpStatus.OK);
    }

    @GetMapping("/bookByAuthorBirthday")
    public ResponseEntity<List<BookAuthorDto>> getBookByAuthorBirthday(@RequestParam int year) {
        return new ResponseEntity<>(authorService.sortByBirthday(year), HttpStatus.OK);
    }

    @GetMapping("/bookByAuthorSecondName")
    public ResponseEntity<List<BookAuthorDto>> getBookByAuthorSecondName(@RequestParam String secondName) {
        return new ResponseEntity<>(authorService.sortBySecondName(secondName), HttpStatus.OK);
    }

    @GetMapping("/bookByAuthorSex")
    public ResponseEntity<List<BookAuthorDto>> getBookByAuthorSex(@RequestParam String sex) {
        return new ResponseEntity<>(authorService.sortBySex(sex), HttpStatus.OK);
    }*/
}
