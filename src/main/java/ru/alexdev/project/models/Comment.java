package ru.alexdev.project.models;

public class Comment {

    private long id;
    private long idBook;
    private long idReader;
    private int rating;
    private String text;

    public Comment() {
    }

    public Comment(long id, long idBook, long idReader, int rating, String text) {
        this.id = id;
        this.idBook = idBook;
        this.idReader = idReader;
        this.rating = rating;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
