package ru.alexdev.project.controllers;

import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alexdev.project.dao.ReaderDAO;
import ru.alexdev.project.models.Reader;

@Controller
@RequestMapping("/readers")
public class ReaderController {

    private final ReaderDAO readerDAO;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReaderController(ReaderDAO readerDAO, JdbcTemplate jdbcTemplate) {
        this.readerDAO = readerDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("readers", readerDAO.index());
        return "readers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("reader", readerDAO.show(id));
        return "readers/show";
    }

    @PostMapping()
    public String create(@ModelAttribute("reader") Reader reader) {
        readerDAO.save(reader);
        return "redirect:/readers";
    }

    @GetMapping("/new")
    public String newReader(@ModelAttribute("reader") Reader reader) {
        return "readers/new";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("reader") Reader reader, @PathVariable("id") int id) {
        readerDAO.update(id, reader);
        return "redirect:/readers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("reader", readerDAO.show(id));
        return "readers/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        readerDAO.delete(id);
        return "redirect:/readers";
    }


}
