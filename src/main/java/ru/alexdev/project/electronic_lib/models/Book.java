package ru.alexdev.project.electronic_lib.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_author", referencedColumnName = "id")
    private Author author;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "available")
    private Boolean isAvailable;

    // Убираем старое поле bookings
    // Добавляем связь через BookCopy (если нужно)
    @OneToMany(mappedBy = "book")
    private List<BookCopy> copies;

    @OneToMany(mappedBy = "book")
    private List<Comment> comments;


    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "id_book"),
            inverseJoinColumns = @JoinColumn(name = "id_genre")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "book")
    private List<Logs> logs;

    @Transient
    private int copiesCount = 1;  // не сохраняется в БД

    public int getCopiesCount() {
        return copiesCount;
    }

    public void setCopiesCount(int copiesCount) {
        this.copiesCount = copiesCount;
    }
    // количество копий, которое мы хотим создать

    public Book() {}

    public Book(String description, String name) {
        this.description = description;
        this.name = name;
    }

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<BookCopy> getCopies() { return copies; }
    public void setCopies(List<BookCopy> copies) { this.copies = copies; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    public List<Genre> getGenres() { return genres; }
    public void setGenres(List<Genre> genres) { this.genres = genres; }

    public List<Logs> getLogs() { return logs; }
    public void setLogs(List<Logs> logs) { this.logs = logs; }

    public Boolean getAvailable() { return isAvailable; }
    public void setAvailable(Boolean available) { isAvailable = available; }

}
