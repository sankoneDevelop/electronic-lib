package ru.alexdev.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.models.Logs;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Integer> {
}
