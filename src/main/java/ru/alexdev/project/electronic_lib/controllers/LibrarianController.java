package ru.alexdev.project.electronic_lib.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexdev.project.electronic_lib.services.LibrarianServices;

@Controller
@RequestMapping("/librarians")
public class LibrarianController {

    private final LibrarianServices librarianServices;

    @Autowired
    public LibrarianController(LibrarianServices librarianServices) {
        this.librarianServices = librarianServices;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("librarian", librarianServices.findOne(id));

        return "librarian/show";
    }
}
