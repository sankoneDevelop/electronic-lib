package ru.alexdev.project.electronic_lib.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.models.Author;
import ru.alexdev.project.electronic_lib.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findOne(int id) {
        return authorRepository.findById(id).orElse(null);
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public void update(int id, Author updatedAuthor) {
        updatedAuthor.setId(id);
        authorRepository.save(updatedAuthor);
    }

    public void delete(int id) {
        authorRepository.deleteById(id);
    }

    public List<Author> searchAuthors(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll();
        }

        String trimmedTerm = searchTerm.trim().toLowerCase();

        return authorRepository.findBySearchTerm(trimmedTerm);
    }
}
