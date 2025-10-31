package ru.alexdev.project.electronic_lib.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.models.AuthUser;
import ru.alexdev.project.electronic_lib.models.Reader;
import ru.alexdev.project.electronic_lib.repositories.AuthUserRepository;
import ru.alexdev.project.electronic_lib.repositories.ReaderRepository;

@Service
public class RegistrationService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReaderRepository readerRepository;

    @Autowired
    public RegistrationService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder, ReaderRepository readerRepository) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.readerRepository = readerRepository;
    }


    @Transactional
    public AuthUser saveTemporary(AuthUser authUser) {
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        authUser.setRole("ROLE_USER");

        return authUserRepository.save(authUser);
    }

    @Transactional
    public void completeRegistration(int userId, Reader reader) {
        AuthUser authUser = authUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        readerRepository.save(reader);
        authUser.setReader(reader);
        authUserRepository.save(authUser);
    }
}
