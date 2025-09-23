package ru.alexdev.project.models;

public class Book {

    private long id;
    private long idReader;
    private long idAuthor;
    private String description;
    private String status;

    public Book() {
    }

    public Book(long id, long idReader, long idAuthor, String description, String status) {
        this.id = id;
        this.idReader = idReader;
        this.idAuthor = idAuthor;
        this.description = description;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdReader() {
        return idReader;
    }

    public void setIdReader(long idReader) {
        this.idReader = idReader;
    }

    public long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
