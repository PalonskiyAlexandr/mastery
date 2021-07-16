package com.palonskiy.controllers;

import com.palonskiy.dto.AuthorDto;
import com.palonskiy.service.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorController {

    private final AuthorService authorService;

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
}
