package ru.alexdev.project.models;

public class Genre {

    private long id;
    private String genreType;

    public Genre() {
    }

    public Genre(long id, String genreType) {
        this.id = id;
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
