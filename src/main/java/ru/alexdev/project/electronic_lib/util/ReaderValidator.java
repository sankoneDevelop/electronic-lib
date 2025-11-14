package ru.alexdev.project.electronic_lib.util;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alexdev.project.electronic_lib.models.Reader;

@Component
public class ReaderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Reader.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Reader reader = (Reader) target;
    }
}
