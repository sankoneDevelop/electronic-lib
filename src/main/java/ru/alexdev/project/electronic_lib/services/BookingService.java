package ru.alexdev.project.electronic_lib.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexdev.project.electronic_lib.models.Book;
import ru.alexdev.project.electronic_lib.models.Booking;
import ru.alexdev.project.electronic_lib.repositories.BookRepository;
import ru.alexdev.project.electronic_lib.repositories.BookingRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, BookRepository bookRepository) {
        this.bookingRepository = bookingRepository;
        this.bookRepository = bookRepository;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAllOrderByActiveFirst();
    }

    public Booking findOne(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Booking booking) {

        Book book = booking.getBook();
        book.setAvailable(false);
        bookRepository.save(book);

        bookingRepository.save(booking);
    }

    @Transactional
    public void returnBook(int id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) return;

        Book book = booking.getBook();
        book.setAvailable(true);

        bookRepository.save(book);
    }

    public Booking findById(int id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public boolean isBookAvailable(int bookId) {
        // Книга доступна только если нет активных броней на неё
        return bookingRepository.findActiveBookingByBookId(bookId).isEmpty();
    }

    public List<Booking> searchBookings(String search) {
        if (search == null || search.trim().isEmpty()) {
            return findAll();
        }

        String trimmedTerm = search.trim().toLowerCase();
        return bookingRepository.findBySearchTerm(trimmedTerm);
    }


}
