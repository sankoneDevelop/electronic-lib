package ru.alexdev.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alexdev.project.models.Reader;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ReaderDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReaderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


////////////////////////////////    Стоит заглушка на лимит в 50 человек ///////////////////////////////////////////////
    public List<Reader> index() {
        return jdbcTemplate.query("SELECT * FROM Reader ORDER BY id LIMIT 50", new BeanPropertyRowMapper<>(Reader.class));
    }

    public Reader show(long id) {
        return jdbcTemplate.query("SELECT * FROM Reader WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Reader.class))
                .stream().findAny().orElse(null);
    }

    public void save(Reader reader) {
        jdbcTemplate.update("INSERT INTO Reader(surname, name, email, phone_number) VALUES(?, ?, ?, ?)",
                reader.getSurname(), reader.getName(), reader.getEmail(), reader.getPhoneNumber());
    }

    public void update(int id, Reader updatedPerson) {
        jdbcTemplate.update("UPDATE Reader SET surname=?, name=?, email=?, phone_number=?",
                updatedPerson.getSurname(), updatedPerson.getName(), updatedPerson.getEmail(), updatedPerson.getPhoneNumber());
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM Reader WHERE id=?", id);
    }







    //////////////////////////// Batch update ////////////////////////////

    public void batchUpdate() {
        List<Reader> readers = create1000Reader();

        jdbcTemplate.batchUpdate("INSERT INTO Reader VALUES(?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, readers.get(i).getId());
                ps.setString(2, readers.get(i).getSurname());
                ps.setString(3, readers.get(i).getName());
                ps.setString(4, readers.get(i).getEmail());
                ps.setString(5, readers.get(i).getPhoneNumber());
            }

            @Override
            public int getBatchSize() {
                return readers.size();
            }
        });

    }

    private List<Reader> create1000Reader() {
        List<Reader>  readers = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            readers.add(new Reader(i, "Surname" + i, "Name" + i, "test" + i + "@test.com", "Number" + i));
        }

        return readers;
    }




}
