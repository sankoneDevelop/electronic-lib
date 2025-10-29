package ru.alexdev.project.electronic_lib.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.models.AuthUser;
import ru.alexdev.project.electronic_lib.repositories.AuthUserRepository;

@Service
public class RegistrationService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void register(AuthUser authUser) {
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        authUser.setRole("ROLE_USER");

        authUserRepository.save(authUser);
    }
}
