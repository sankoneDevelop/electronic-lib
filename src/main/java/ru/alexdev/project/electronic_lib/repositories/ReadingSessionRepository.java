package ru.alexdev.project.electronic_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.electronic_lib.models.ReadingSession;

@Repository
public interface ReadingSessionRepository extends JpaRepository<ReadingSession, Integer> {
}
