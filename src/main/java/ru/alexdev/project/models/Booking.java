package ru.alexdev.project.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {

    private long idBook;
    private long idReader;
    private LocalDateTime date;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;

    public Booking() {
    }

    public Booking(long idBook, long idReader, LocalDateTime date, LocalDateTime dueDate, LocalDateTime returnDate) {
        this.idBook = idBook;
        this.idReader = idReader;
        this.date = date;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public long getIdBook() {
        return idBook;
    }

    public void setIdBook(long idBook) {
        this.idBook = idBook;
    }

    public long getIdReader() {
        return idReader;
    }

    public void setIdReader(long idReader) {
        this.idReader = idReader;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
}
