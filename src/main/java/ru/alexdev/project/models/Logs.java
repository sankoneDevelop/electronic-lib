package ru.alexdev.project.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Logs")
public class Logs {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_reader", referencedColumnName = "id")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "id_book", referencedColumnName = "id")
    private Book book;

    private String actionType; // To Do - сменить на Enum

    private LocalDateTime timestamp;


    public Logs() {
    }

    public Logs(long id, String actionType, LocalDateTime timestamp) {
        this.id = id;
        this.actionType = actionType;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


}
