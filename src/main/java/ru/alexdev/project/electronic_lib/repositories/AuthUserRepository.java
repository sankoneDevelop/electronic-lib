package ru.alexdev.project.electronic_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.electronic_lib.models.AuthUser;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {

    Optional<AuthUser> findByUsername(String username);
}
