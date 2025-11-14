package ru.alexdev.project.electronic_lib.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.models.AuthUser;
import ru.alexdev.project.electronic_lib.models.Librarian;
import ru.alexdev.project.electronic_lib.models.Reader;
import ru.alexdev.project.electronic_lib.repositories.AuthUserRepository;
import ru.alexdev.project.electronic_lib.repositories.LibrarianRepository;
import ru.alexdev.project.electronic_lib.repositories.ReaderRepository;

@Service
public class RegistrationService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final LibrarianRepository librarianRepository;
    @Autowired
    public RegistrationService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder, LibrarianRepository librarianRepository) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.librarianRepository = librarianRepository;
    }


    @Transactional
    public AuthUser saveTemporary(AuthUser authUser) {
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));

        return authUserRepository.save(authUser);
    }

    @Transactional
    public void completeRegistration(int userId, Librarian librarian) {
        AuthUser authUser = authUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (librarianRepository.findByPhoneNumber(librarian.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Библиотекарь> с таким номером телефона уже существует");
        }

        if (librarianRepository.findByEmail(librarian.getEmail()).isPresent()) {
            throw new RuntimeException("Библиотекарь с таким email уже существует");
        }

        librarianRepository.save(librarian);
        authUser.setLibrarian(librarian);
        authUserRepository.save(authUser);
    }
}
