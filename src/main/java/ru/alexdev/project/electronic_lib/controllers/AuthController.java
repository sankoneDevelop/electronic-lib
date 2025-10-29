package ru.alexdev.project.electronic_lib.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexdev.project.electronic_lib.models.AuthUser;
import ru.alexdev.project.electronic_lib.services.RegistrationService;
import ru.alexdev.project.electronic_lib.util.AuthUserValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserValidator authUserValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(AuthUserValidator authUserValidator, RegistrationService registrationService) {
        this.authUserValidator = authUserValidator;
        this.registrationService = registrationService;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("auth_user") AuthUser authUser) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("auth_user") @Valid AuthUser authUser,
                                      BindingResult bindingResult) {

        authUserValidator.validate(authUser, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";

        registrationService.register(authUser);

        return "redirect:/auth/login";

    }
}

