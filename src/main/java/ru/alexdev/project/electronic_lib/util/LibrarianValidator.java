package ru.alexdev.project.electronic_lib.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alexdev.project.electronic_lib.models.Librarian;

@Component
public class LibrarianValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Librarian.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Librarian librarian = (Librarian) target;

    }
}
