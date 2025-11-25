package ru.alexdev.project.electronic_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.electronic_lib.models.Booking;
import ru.alexdev.project.electronic_lib.models.BookingStatus;
import ru.alexdev.project.electronic_lib.models.Reader;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // Найти активное бронирование конкретной копии книги
    @Query("SELECT b FROM Booking b " +
            "WHERE b.bookCopy.id = :copyId " +
            "AND b.status = ru.alexdev.project.electronic_lib.models.BookingStatus.ACTIVE")
    List<Booking> findActiveBookingByCopyId(@Param("copyId") int copyId);

    // Сортировка: сначала активные бронирования
    @Query("SELECT b FROM Booking b ORDER BY " +
            "CASE WHEN b.status = ru.alexdev.project.electronic_lib.models.BookingStatus.ACTIVE THEN 0 ELSE 1 END, " +
            "b.startDate DESC")
    List<Booking> findAllOrderByActiveFirst();

    // Поиск по названию книги, ФИО читателя и статусу
    @Query("SELECT b FROM Booking b WHERE " +
            "LOWER(b.bookCopy.book.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(CONCAT(b.reader.surname, ' ', b.reader.name)) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(CAST(b.status AS string)) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Booking> findBySearchTerm(@Param("search") String search);

    List<Booking> findByReaderAndStatus(Reader reader, BookingStatus status);
}
