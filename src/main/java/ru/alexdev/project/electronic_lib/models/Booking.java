package ru.alexdev.project.electronic_lib.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;


    @ManyToOne
    @JoinColumn(name = "book_copy_id", referencedColumnName = "id")
    private BookCopy bookCopy;

    @ManyToOne
    @JoinColumn(name = "id_reader", referencedColumnName = "id")
    private Reader reader;

    @Column(name = "date")
    private LocalDateTime startDate;

    @Column(name = "return_date")
    private LocalDateTime finishDate;

    public Booking() {
        this.status = BookingStatus.ACTIVE;
    }

    public Booking(LocalDateTime startDate, LocalDateTime finishDate) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.status = BookingStatus.ACTIVE;
    }

    // getters/setters
    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }

    public BookCopy getBookCopy() { return bookCopy; }
    public void setBookCopy(BookCopy bookCopy) { this.bookCopy = bookCopy; }

    public Reader getReader() { return reader; }
    public void setReader(Reader reader) { this.reader = reader; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getFinishDate() { return finishDate; }
    public void setFinishDate(LocalDateTime finishDate) { this.finishDate = finishDate; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
