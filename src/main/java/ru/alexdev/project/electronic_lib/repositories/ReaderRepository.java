package ru.alexdev.project.electronic_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.electronic_lib.models.Reader;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {

    @Query("SELECT r FROM Reader r LEFT JOIN FETCH r.books WHERE r.id = :id")
    Optional<Reader> findByIdWithBooks(@Param("id") int id);

    Optional<Reader> findByPhoneNumber(String phoneNumber);

    Optional<Reader> findByEmail(String email);

}
