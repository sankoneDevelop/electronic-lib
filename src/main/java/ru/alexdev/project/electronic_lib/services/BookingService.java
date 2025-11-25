package ru.alexdev.project.electronic_lib.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexdev.project.electronic_lib.models.BookCopy;
import ru.alexdev.project.electronic_lib.models.Booking;
import ru.alexdev.project.electronic_lib.models.Reader;
import ru.alexdev.project.electronic_lib.repositories.BookCopyRepository;
import ru.alexdev.project.electronic_lib.repositories.BookingRepository;
import ru.alexdev.project.electronic_lib.models.BookingStatus;


import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookCopyRepository bookCopyRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          BookCopyRepository bookCopyRepository) {
        this.bookingRepository = bookingRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAllOrderByActiveFirst();
    }

    public Booking findOne(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Booking booking) {

        // Проверка: копия уже забронирована?
        List<Booking> active = bookingRepository.findActiveBookingByCopyId(
                booking.getBookCopy().getId()
        );
        if (!active.isEmpty()) {
            throw new RuntimeException("Эта копия книги уже забронирована.");
        }

        // Делаем копию недоступной
        BookCopy copy = booking.getBookCopy();
        if (copy != null) {
            copy.setAvailable(false);
            bookCopyRepository.save(copy);
        }

        // статус при создании
        booking.setStatus(BookingStatus.ACTIVE);
        bookingRepository.save(booking);
    }

    @Transactional
    public void returnBook(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CLOSED) {
            return; // уже возвращено
        }

        // Меняем статус брони
        booking.setStatus(BookingStatus.CLOSED);
        booking.setFinishDate(LocalDateTime.now());

        // Освобождаем книгу
        BookCopy copy = booking.getBookCopy();
        if (copy != null) {
            copy.setAvailable(true);
            bookCopyRepository.save(copy); // сохраняем изменения доступности
        }

        bookingRepository.save(booking); // сохраняем изменения брони
    }


    public Booking findById(int id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public boolean isCopyAvailable(int copyId) {
        return bookCopyRepository.findById(copyId)
                .map(BookCopy::isAvailable)
                .orElse(false);
    }

    public List<Booking> searchBookings(String search) {
        if (search == null || search.trim().isEmpty()) {
            return findAll();
        }
        return bookingRepository.findBySearchTerm(search.trim().toLowerCase());
    }

    public List<Booking> findByReaderAndStatus(Reader reader, BookingStatus status) {
       return bookingRepository.findByReaderAndStatus(reader, status);

    }
}

