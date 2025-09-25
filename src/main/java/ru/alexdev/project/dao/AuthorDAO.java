package ru.alexdev.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alexdev.project.models.Author;

import java.util.List;

@Component
public class AuthorDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Author> index() {
        return jdbcTemplate.query("SELECT * FROM Author", new BeanPropertyRowMapper<>(Author.class));
    }

    public Author show(long id) {
        return jdbcTemplate.query("SELECT * FROM Author WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Author.class))
                .stream().findAny().orElse(null);
    }

    public void save(Author author) {
        jdbcTemplate.update("INSERT INTO Author(full_name) VALUES(?)", author.getFullName());
    }

    public void update(long id, Author updatedAuthor) {
        jdbcTemplate.update("UPDATE Author SET full_name=? WHERE id=?", updatedAuthor.getFullName(), id);
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM Author WHERE id=?", id);
    }

}
