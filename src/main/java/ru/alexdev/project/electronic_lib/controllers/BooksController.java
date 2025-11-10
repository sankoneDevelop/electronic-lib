package ru.alexdev.project.electronic_lib.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.alexdev.project.electronic_lib.models.AuthUser;
import ru.alexdev.project.electronic_lib.models.Book;
import ru.alexdev.project.electronic_lib.models.Reader;
import ru.alexdev.project.electronic_lib.services.AuthUserService;
import ru.alexdev.project.electronic_lib.services.AuthorService;
import ru.alexdev.project.electronic_lib.services.BookService;
import ru.alexdev.project.electronic_lib.services.ReaderService;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final ReaderService readerService;
    private final AuthUserService authUserService;


    @Autowired
    public BooksController(BookService bookService, AuthorService authorService, ReaderService readerService, AuthUserService authUserService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.readerService = readerService;
        this.authUserService = authUserService;
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
    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("author", book.getAuthor());
        return "books/show";
    }

    @PostMapping("/{id}/take")
    public RedirectView takeBook(@PathVariable("id") int id, Authentication authentication) {
        RedirectView redirectView = new RedirectView("/books/" + id);
        try {
            String username = authentication.getName();
            readerService.takeBook(id, username);
            redirectView.addStaticAttribute("success", "true");
        } catch (RuntimeException e) {
            redirectView.addStaticAttribute("error", e.getMessage());
        }
        return redirectView;
    }

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

