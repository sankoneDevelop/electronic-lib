package ru.alexdev.project.electronic_lib.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String index(@RequestParam(value = "search", required = false) String search,
                        Model model,
                        Authentication authentication) {

        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("readers", readerService.searchReaders(search.trim()));
        } else {
            model.addAttribute("readers", readerService.findAll());
        }

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        return "readers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("reader", readerService.findOne(id));

        model.addAttribute("books", bookService.getBooksByReaderId(id));
        return "readers/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("reader") @Valid Reader reader,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "readers/new";
        }

        try {
            readerService.save(reader);
            return "redirect:/readers";
        } catch (IllegalArgumentException e) {
            // Добавляем ошибку уникальности в BindingResult
            if (e.getMessage().contains("email")) {
                bindingResult.rejectValue("email", "error.reader", e.getMessage());
            } else if (e.getMessage().contains("номером телефона")) {
                bindingResult.rejectValue("phoneNumber", "error.reader", e.getMessage());
            } else {
                bindingResult.reject("error.reader", e.getMessage());
            }
            return "readers/new";
        }
    }

    @GetMapping("/new")
    public String newReader(@ModelAttribute("reader") Reader reader) {
        return "readers/new";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("reader") @Valid Reader reader,
                         BindingResult bindingResult,
                         @PathVariable("id") int id,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "readers/edit";
        }

        try {
            readerService.update(id, reader);
            return "redirect:/readers";
        } catch (IllegalArgumentException e) {
            // Добавляем ошибку уникальности в BindingResult
            if (e.getMessage().contains("email")) {
                bindingResult.rejectValue("email", "error.reader", e.getMessage());
            } else if (e.getMessage().contains("номером телефона")) {
                bindingResult.rejectValue("phoneNumber", "error.reader", e.getMessage());
            } else if (e.getMessage().contains("не найден")) {
                bindingResult.reject("error.reader", e.getMessage());
            } else {
                bindingResult.reject("error.reader", e.getMessage());
            }

            // Добавляем reader обратно в модель для отображения формы
            model.addAttribute("reader", reader);
            return "readers/edit";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Reader reader = readerService.findOne(id);
        if (reader == null) {
            return "redirect:/readers";
        }
        model.addAttribute("reader", reader);
        return "readers/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        readerService.delete(id);
        return "redirect:/readers";
    }
}