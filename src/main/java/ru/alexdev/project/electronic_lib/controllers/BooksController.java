package ru.alexdev.project.electronic_lib.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.alexdev.project.electronic_lib.models.*;
import ru.alexdev.project.electronic_lib.repositories.ReaderRepository;
import ru.alexdev.project.electronic_lib.services.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookCopyService bookCopyService;


    @Autowired
    public BooksController(BookService bookService, AuthorService authorService, BookCopyService bookCopyService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookCopyService = bookCopyService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "search", required = false) String search, Model model, Authentication authentication) {

        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("books", bookService.searchBooks(search));
        } else {
            model.addAttribute("books", bookService.findAll());
        }

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();

        model.addAttribute("isAuthenticated", isAuthenticated);

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model, Authentication authentication) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("author", book.getAuthor());

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());

        model.addAttribute("isAuthenticated", isAuthenticated);


        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        return "books/edit";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/new";
        }

        bookService.save(book);

        int count = book.getCopiesCount();
        for (int i = 0; i < count; i++) {
            BookCopy copy = new BookCopy();
            copy.setBook(book);
            copy.setAvailable(true);
            copy.setCondition("Новая");
            bookCopyService.save(copy);
        }

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

