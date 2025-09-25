package ru.alexdev.project.models;

public class Book {

    private Integer id;
    private Integer idReader;
    private Integer idAuthor;
    private String description;
    private String name;

    public Book() {}

    public Book(Integer id, Integer idReader, Integer idAuthor, String description, String name) {
        this.id = id;
        this.idReader = idReader;
        this.idAuthor = idAuthor;
        this.description = description;
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdReader() { return idReader; }
    public void setIdReader(Integer idReader) { this.idReader = idReader; }

    public Integer getIdAuthor() { return idAuthor; }
    public void setIdAuthor(Integer idAuthor) { this.idAuthor = idAuthor; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
