package ru.alexdev.project.electronic_lib.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.models.Librarian;
import ru.alexdev.project.electronic_lib.repositories.LibrarianRepository;

import java.util.List;

@Service
@Transactional
public class LibrarianServices {

    private final LibrarianRepository librarianRepository;

    @Autowired
    public LibrarianServices(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public List<Librarian> findAll() {
        return librarianRepository.findAll();
    }

    public Librarian findOne(int id) {
        return librarianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Librarian not found"));
    }

    public void save(Librarian librarian) {
        librarianRepository.save(librarian);
    }

    public void update(int id, Librarian librarianToUpdated) {
        librarianToUpdated.setId(id);
        librarianRepository.save(librarianToUpdated);
    }

    public void delete(int id) {
        librarianRepository.deleteById(id);
    }

    public Librarian findLibrarianByAuthUserUsername(String username) {
        return librarianRepository.findByAuthUserUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
