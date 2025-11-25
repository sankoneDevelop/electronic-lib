package ru.alexdev.project.electronic_lib.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexdev.project.electronic_lib.services.BookService;

@Controller
@RequestMapping("/main")
public class MainController {

    private final BookService bookService;

    @Autowired
    public MainController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public String mainPage(Model model) {

        int countAllBooks = bookService.getCountAllBooks();

        model.addAttribute("countAllBooks", countAllBooks);
        return "main/index";
    }

}
