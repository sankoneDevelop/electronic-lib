package ru.alexdev.project.electronic_lib.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.models.AuthUser;
import ru.alexdev.project.electronic_lib.repositories.AuthUserRepository;

import java.util.Optional;

@Service
public class AuthUserService {

    private final AuthUserRepository authUserRepository;

    @Autowired
    public AuthUserService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    public Optional<AuthUser> findByUsername(String username) {
        return authUserRepository.findByUsername(username);
    }
}
