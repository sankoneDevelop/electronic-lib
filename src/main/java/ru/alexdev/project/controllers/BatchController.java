package ru.alexdev.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexdev.project.dao.ReaderDAO;

@Controller
@RequestMapping("/batch-update")
public class BatchController {

    private final ReaderDAO readerDAO;

    @Autowired
    public BatchController(ReaderDAO readerDAO) {
        this.readerDAO = readerDAO;
    }

    @GetMapping
    public String index() {
        return "batch/index";
    }

//    @GetMapping("/update")
//    public String batchUpdate() {
//        readerDAO.batchUpdate();
//        return "redirect:/readers";
//    }
}
