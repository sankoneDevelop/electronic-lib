package ru.alexdev.project.electronic_lib.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alexdev.project.electronic_lib.models.Author;
import ru.alexdev.project.electronic_lib.services.AuthorService;


@Controller
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorService authorService;

    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorService.findOne(id));
        return "authors/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("author") Author author) {
        authorService.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/new")
    public String newAuthor(@ModelAttribute("author") Author author) {
        return "authors/new";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("author") Author author) {
        authorService.update(id, author);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorService.findOne(id));
        return "authors/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        authorService.delete(id);
        return "redirect:/authors";
    }
}
