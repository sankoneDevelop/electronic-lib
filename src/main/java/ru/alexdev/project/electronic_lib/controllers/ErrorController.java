package ru.alexdev.project.electronic_lib.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "errors/403";
    }
}
