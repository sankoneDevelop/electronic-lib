package ru.alexdev.project.electronic_lib.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alexdev.project.electronic_lib.models.AuthUser;
import ru.alexdev.project.electronic_lib.models.Librarian;
import ru.alexdev.project.electronic_lib.models.Reader;
import ru.alexdev.project.electronic_lib.services.AuthUserService;
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
    public String registrationPage(@ModelAttribute("authUser") AuthUser authUser) {
        return "auth/registration_step1";
    }

    @PostMapping("/registration-step1")
    public String processStep1(@ModelAttribute("authUser") @Valid AuthUser authUser,
                                      BindingResult bindingResult, Model model) {

        authUserValidator.validate(authUser, bindingResult);

        if (bindingResult.hasErrors())
            return "auth/registration_step1";

        AuthUser authUser1 =  registrationService.saveTemporary(authUser);

        model.addAttribute("librarian", new Librarian());
        model.addAttribute("authUserId", authUser1.getId());


        return "auth/registration_step2";

    }

    @PostMapping("/registration-step2")
    public String performRegistration(@ModelAttribute("reader") @Valid Librarian librarian,
                                      BindingResult bindingResult,
                                      @RequestParam("authUserId") int authUserId,
                                      Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("authUserId", authUserId);
            return "auth/registration_step2";
        }

        try {
            registrationService.completeRegistration(authUserId, librarian);
            return "redirect:/auth/login?success";
        } catch (RuntimeException e) {

            bindingResult.rejectValue("phoneNumber", "error.phoneNumber", e.getMessage());
            model.addAttribute("authUserId", authUserId);
            return "auth/registration_step2";

        }
    }
}

