package ru.alexdev.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.models.ReadingSession;

@Repository
public interface ReadingSessionRepository extends JpaRepository<ReadingSession, Integer> {
}
