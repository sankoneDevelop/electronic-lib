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
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(long id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(id_author, description, name) VALUES (?, ?, ?)", book.getIdAuthor(), book.getDescription(), book.getName());
    }

    public void update(long id, Book book) {
        jdbcTemplate.update("UPDATE Book SET id_author=?, description=?, name=? WHERE id=?", book.getIdAuthor(), book.getDescription(), book.getName(), book.getId());
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

}
