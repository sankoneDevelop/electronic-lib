package ru.alexdev.project.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_author", referencedColumnName = "id")
    private Author author;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "book_reader",
            joinColumns = @JoinColumn(name = "id_book"),
            inverseJoinColumns = @JoinColumn(name = "id_reader")
    )
    private List<Reader> readers;

    @OneToMany(mappedBy = "book")
    private List<ReadingSession> readingSessions;

    @OneToMany(mappedBy = "book")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "id_book"),
            inverseJoinColumns = @JoinColumn(name = "id_genre")
    )
    private List<Genre> genres;

    public Book() {}

    public Book(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ReadingSession> getReadingSessions() {
        return readingSessions;
    }

    public void setReadingSessions(List<ReadingSession> readingSessions) {
        this.readingSessions = readingSessions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
