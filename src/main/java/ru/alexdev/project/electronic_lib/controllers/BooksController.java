package ru.alexdev.project.electronic_lib.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alexdev.project.electronic_lib.models.Book;
import ru.alexdev.project.electronic_lib.services.AuthorService;
import ru.alexdev.project.electronic_lib.services.BookService;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BooksController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/index";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model) {
//        Book book = bookDAO.show(id);
//        model.addAttribute("book", book);
//        model.addAttribute("author", authorDAO.show(book.getIdAuthor()));
//        return "books/show";
//    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        return "books/edit";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book, Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "books/new";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}

