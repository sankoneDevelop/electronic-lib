package ru.alexdev.project.electronic_lib.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alexdev.project.electronic_lib.models.Reader;
import ru.alexdev.project.electronic_lib.services.BookService;
import ru.alexdev.project.electronic_lib.services.ReaderService;

@Controller
@RequestMapping("/readers")
public class ReaderController {

    private final BookService bookService;
    private final ReaderService readerService;

    @Autowired
    public ReaderController(BookService bookService, ReaderService readerService) {
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("readers", readerService.findAll());
        return "readers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("reader", readerService.findOne(id));
        model.addAttribute("books", bookService.getBooksByReaderId(id));
        return "readers/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("reader") Reader reader) {
        readerService.save(reader);
        return "redirect:/readers";
    }

    @GetMapping("/new")
    public String newReader(@ModelAttribute("reader") Reader reader) {
        return "readers/new";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("reader") Reader reader, @PathVariable("id") int id) {
        readerService.update(id, reader);
        return "redirect:/readers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("reader", readerService.findOne(id));
        return "readers/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        readerService.delete(id);
        return "redirect:/readers";
    }


}
