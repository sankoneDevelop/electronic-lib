package ru.alexdev.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alexdev.project.dao.AuthorDAO;
import ru.alexdev.project.models.Author;

@Controller
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorDAO authorDAO;

    @Autowired
    public AuthorsController(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("authors", authorDAO.index());
        return "authors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.show(id));
        return "authors/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("author") Author author) {
        authorDAO.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/new")
    public String newAuthor(@ModelAttribute("author") Author author) {
        return "authors/new";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("author") Author author) {
        authorDAO.update(id, author);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("author", authorDAO.show(id));
        return "authors/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        authorDAO.delete(id);
        return "redirect:/authors";
    }
}
