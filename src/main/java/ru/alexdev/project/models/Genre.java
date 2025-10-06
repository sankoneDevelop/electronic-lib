package ru.alexdev.project.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Genre")
public class Genre {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "genre_type")
    private String genreType;

    @ManyToMany(mappedBy = "genres")
    private List<Book> books;

    public Genre() {
    }

    public Genre(String genreType) {
        this.genreType = genreType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenreType() {
        return genreType;
    }

    public void setGenreType(String genreType) {
        this.genreType = genreType;
    }
}
