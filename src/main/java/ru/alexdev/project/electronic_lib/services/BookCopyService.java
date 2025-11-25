package ru.alexdev.project.electronic_lib.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexdev.project.electronic_lib.models.BookCopy;
import ru.alexdev.project.electronic_lib.repositories.BookCopyRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;

    @Autowired
    public BookCopyService(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    public List<BookCopy> findAll() {
        return bookCopyRepository.findAll();
    }

    public List<BookCopy> findAvailableCopies() {
        return bookCopyRepository.findByAvailableTrue();
    }

    public BookCopy findById(int id) {
        return bookCopyRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(BookCopy copy) {
        bookCopyRepository.save(copy);
    }
}
