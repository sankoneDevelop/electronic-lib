package ru.alexdev.project.electronic_lib.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.models.Book;
import ru.alexdev.project.electronic_lib.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void save(Book book) {
        book.setAvailable(true);
        bookRepository.save(book);
    }

    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public int getCountAllBooks() {
        return bookRepository.findCountAllBooks();
    }

    public List<Book> searchBooks(String search) {
        if (search == null && search.trim().isEmpty()) {
            return findAll();
        }

        String trimmedTerm = search.trim().toLowerCase();

        return bookRepository.findBySearchTerm(trimmedTerm);
    }

    public Book findById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

}
