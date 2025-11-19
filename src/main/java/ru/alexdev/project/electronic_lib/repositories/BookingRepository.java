package ru.alexdev.project.electronic_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.electronic_lib.models.Booking;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b WHERE b.book.id = :bookId AND b.status = ru.alexdev.project.electronic_lib.models.BookingStatus.ACTIVE")
    List<Booking> findActiveBookingByBookId(int bookId);

    @Query("SELECT b FROM Booking b ORDER BY " +
            "CASE WHEN b.status = ru.alexdev.project.electronic_lib.models.BookingStatus.ACTIVE THEN 0 ELSE 1 END, " +
            "b.startDate DESC")
    List<Booking> findAllOrderByActiveFirst();

    @Query("SELECT b FROM Booking b WHERE " +
            "LOWER(b.book.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(CONCAT(b.reader.surname, ' ', b.reader.name)) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(b.status) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Booking> findBySearchTerm(@Param("search") String search);

}
