package ru.alexdev.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
