package ru.alexdev.project.electronic_lib.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
public class ReadingSession {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_book", referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "id_reader", referencedColumnName = "id")
    private Reader reader;

    @Column(name = "date")
    private LocalDateTime startDate;

    @Column(name = "return_date")
    private LocalDateTime finishDate;


    public ReadingSession() {
    }

    public ReadingSession(LocalDateTime startDate, LocalDateTime finishDate) {
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime date) {
        this.startDate = date;
    }



    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime returnDate) {
        this.finishDate = returnDate;
    }


}
