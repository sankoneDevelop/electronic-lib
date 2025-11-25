package ru.alexdev.project.electronic_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.electronic_lib.models.BookCopy;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Integer> {

    List<BookCopy> findByAvailableTrue();

}

