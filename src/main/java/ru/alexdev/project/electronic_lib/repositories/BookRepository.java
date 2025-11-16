package ru.alexdev.project.electronic_lib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alexdev.project.electronic_lib.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b JOIN b.readers r WHERE  r.id = :id ")
    List<Book> findBooksByReaderId(@Param("id") int id);

    @Query("SELECT COUNT(b) FROM Book b WHERE SIZE(b.readers) > 0")
    Integer findCountTakenBooks();

    @Query("SELECT COUNT(b) FROM Book b")
    Integer findCountAllBooks();

    @Query(value = "SELECT b FROM Book b WHERE " +
            "LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Book> findBySearchTerm(String search);
}
