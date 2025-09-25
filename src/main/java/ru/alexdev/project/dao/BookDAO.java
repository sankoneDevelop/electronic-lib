package ru.alexdev.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alexdev.project.models.Book;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query(
                "SELECT id, id_reader AS idReader, id_author AS idAuthor, name, description FROM book",
                new BeanPropertyRowMapper<>(Book.class)
        );
    }

    public Book show(int id) {
        return jdbcTemplate.query(
                "SELECT id, id_reader AS idReader, id_author AS idAuthor, name, description FROM book WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)
        ).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update(
                "INSERT INTO book(name, description, id_reader, id_author) VALUES (?, ?, ?, ?)",
                book.getName(),
                book.getDescription(),
                book.getIdReader(),
                book.getIdAuthor()
        );
    }

    public void update(int id, Book book) {
        jdbcTemplate.update(
                "UPDATE book SET name=?, description=?, id_reader=?, id_author=? WHERE id=?",
                book.getName(),
                book.getDescription(),
                book.getIdReader(),
                book.getIdAuthor(),
                id
        );
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }
}

