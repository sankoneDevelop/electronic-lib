package ru.alexdev.project.electronic_lib.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.models.Book;
import ru.alexdev.project.electronic_lib.models.Reader;
import ru.alexdev.project.electronic_lib.repositories.BookRepository;
import ru.alexdev.project.electronic_lib.repositories.ReaderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository, BookRepository bookRepository) {
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public Reader findOne(int id) {
        return readerRepository.findByIdWithBookings(id)
                .orElseThrow(() -> new RuntimeException("Reader not found"));
    }


    public void save(Reader reader) {
        // Проверка уникальности email
        if (reader.getEmail() != null && !reader.getEmail().trim().isEmpty()) {
            Optional<Reader> existingByEmail = readerRepository.findByEmail(reader.getEmail().trim());
            if (existingByEmail.isPresent()) {
                throw new IllegalArgumentException("Читатель с email '" + reader.getEmail() + "' уже существует");
            }
        }

        // Проверка уникальности номера телефона
        if (reader.getPhoneNumber() != null && !reader.getPhoneNumber().trim().isEmpty()) {
            Optional<Reader> existingByPhone = readerRepository.findByPhoneNumber(reader.getPhoneNumber().trim());
            if (existingByPhone.isPresent()) {
                throw new IllegalArgumentException("Читатель с номером телефона '" + reader.getPhoneNumber() + "' уже существует");
            }
        }

        readerRepository.save(reader);
    }

    public void update(int id, Reader updatedReader) {
        // Проверяем существование читателя
        Reader existingReader = findOne(id);
        if (existingReader == null) {
            throw new IllegalArgumentException("Читатель с ID " + id + " не найден");
        }

        // Проверка уникальности email
        if (updatedReader.getEmail() != null && !updatedReader.getEmail().trim().isEmpty()) {
            Optional<Reader> existingByEmail = readerRepository.findByEmail(updatedReader.getEmail().trim());
            if (existingByEmail.isPresent() && existingByEmail.get().getId() != id) {
                throw new IllegalArgumentException("Читатель с email '" + updatedReader.getEmail() + "' уже существует");
            }
        }

        // Проверка уникальности номера телефона
        if (updatedReader.getPhoneNumber() != null && !updatedReader.getPhoneNumber().trim().isEmpty()) {
            Optional<Reader> existingByPhone = readerRepository.findByPhoneNumber(updatedReader.getPhoneNumber().trim());
            if (existingByPhone.isPresent() && existingByPhone.get().getId() != id) {
                throw new IllegalArgumentException("Читатель с номером телефона '" + updatedReader.getPhoneNumber() + "' уже существует");
            }
        }

        updatedReader.setId(id);
        readerRepository.save(updatedReader);
    }

    public void delete(int id) {
        readerRepository.deleteById(id);
    }

    public List<Reader> searchReaders(String searchTerm) {
        System.out.println("Запрос: " + searchTerm);

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll();
        }

        String trimmedTerm = searchTerm.trim().toLowerCase();


        System.out.println("Триммд запрос: " + trimmedTerm);

        // Поиск по ID
        try {
            int id = Integer.parseInt(trimmedTerm);
            Reader readerById = findOne(id);
            if (readerById != null) {
                return List.of(readerById);
            }
        } catch (NumberFormatException e) {
            // Не число — продолжаем поиск по тексту
        }

        // Поиск по тексту
        return readerRepository.findBySearchTerm(trimmedTerm);
    }

    public Reader findById(int id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reader not found"));
    }
}