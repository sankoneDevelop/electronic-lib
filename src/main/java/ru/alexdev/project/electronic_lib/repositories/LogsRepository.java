package ru.alexdev.project.electronic_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.electronic_lib.models.Logs;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Integer> {
}
