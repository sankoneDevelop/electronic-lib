package ru.alexdev.project.electronic_lib.controllers;

import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public String index(@RequestParam(value = "search", required = false) String search, Model model, Authentication authentication, ServletRequest servletRequest) {

        if (search != null && !(search.trim().isEmpty())) {
            model.addAttribute("authors", authorService.searchAuthors(search.trim()));
        } else {
            model.addAttribute("authors", authorService.findAll());

        }

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        return "authors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        var author = authorService.findOne(id);
        System.out.println("AUTHOR FOUND: " + author);
        model.addAttribute("author", author);
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
