package ru.alexdev.project.models;

public class Book {

    private long id;
    private long idReader;
    private long idAuthor;
    private String description;
    private String name;

    public Book() {
    }

    public Book(long id, long idReader, long idAuthor, String description, String name) {
        this.id = id;
        this.idReader = idReader;
        this.idAuthor = idAuthor;
        this.description = description;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
