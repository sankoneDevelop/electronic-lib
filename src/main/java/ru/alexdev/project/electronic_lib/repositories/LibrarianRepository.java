package ru.alexdev.project.electronic_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.electronic_lib.models.Librarian;

import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Integer> {

    @Query("SELECT l FROM Librarian l JOIN l.authUser au WHERE au.username = :username")
    Optional<Librarian> findByAuthUserUsername(String username);

    Optional<Librarian> findByPhoneNumber(String phoneNumber);

    Optional<Librarian> findByEmail(String email);
}
