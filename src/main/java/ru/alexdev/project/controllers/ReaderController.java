package ru.alexdev.project.controllers;

import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexdev.project.dao.ReaderDAO;

@Controller
@RequestMapping("/readers")
public class ReaderController {

    private final ReaderDAO readerDAO;

    @Autowired
    public ReaderController(ReaderDAO readerDAO) {
        this.readerDAO = readerDAO;
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
}
