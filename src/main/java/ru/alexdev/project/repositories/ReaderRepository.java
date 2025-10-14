package ru.alexdev.project.repositories;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.models.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {
}
