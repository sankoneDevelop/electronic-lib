package ru.alexdev.project.electronic_lib.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "book_copy")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    private String condition;
    private boolean available = true;

    @OneToMany(mappedBy = "bookCopy")
    private List<Booking> bookings;

    public BookCopy() {}

    public BookCopy(Book book, String condition) {
        this.book = book;
        this.condition = condition;
        this.available = true;
    }

    // getters/setters
    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
}
