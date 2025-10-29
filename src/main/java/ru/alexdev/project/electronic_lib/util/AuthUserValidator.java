package ru.alexdev.project.electronic_lib.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alexdev.project.electronic_lib.models.AuthUser;
import ru.alexdev.project.electronic_lib.services.AuthUserService;

@Component
public class AuthUserValidator implements Validator {

    private final AuthUserService authUserService;

    @Autowired
    public AuthUserValidator(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AuthUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        AuthUser authUser = (AuthUser) target;

        if ((authUserService.findByUsername(authUser.getUsername())).isPresent())
            errors.rejectValue("username", "", "Пользователь с таким логином уже существует!");

    }
}
