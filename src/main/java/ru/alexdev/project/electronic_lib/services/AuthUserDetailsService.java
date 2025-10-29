package ru.alexdev.project.electronic_lib.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.details.AuthUserDetails;
import ru.alexdev.project.electronic_lib.models.AuthUser;
import ru.alexdev.project.electronic_lib.repositories.AuthUserRepository;

import java.util.Optional;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Autowired
    public AuthUserDetailsService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> authUser = authUserRepository.findByUsername(username);

        if (authUser.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new AuthUserDetails(authUser.get());
    }
}
