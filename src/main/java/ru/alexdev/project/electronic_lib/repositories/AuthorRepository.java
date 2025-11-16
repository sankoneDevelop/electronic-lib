package ru.alexdev.project.electronic_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.electronic_lib.models.Author;

import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query(value = "SELECT a From Author a WHERE " +
            "LOWER(a.fullName) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Author> findBySearchTerm(String search);
}
