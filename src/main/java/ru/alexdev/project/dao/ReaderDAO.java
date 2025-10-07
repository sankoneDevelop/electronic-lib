package ru.alexdev.project.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    private final SessionFactory sessionFactory;

    @Autowired
    public ReaderDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional
    public List<Reader> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("FROM Reader", Reader.class).getResultList();
    }

    @Transactional
    public Reader show(long id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Reader.class, id);
    }

    @Transactional
    public void save(Reader reader) {
        Session session = sessionFactory.getCurrentSession();

        session.save(reader);
    }

    @Transactional
    public void update(int id, Reader updatedPerson) {
        Session session = sessionFactory.getCurrentSession();

        Reader reader = session.get(Reader.class, id);

        reader.setEmail(updatedPerson.getEmail());
        reader.setName(updatedPerson.getName());
        reader.setSurname(updatedPerson.getSurname());
        reader.setPhoneNumber(updatedPerson.getPhoneNumber());


    }

    @Transactional
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(session.get(Reader.class, id));

    }







    //////////////////////////// Batch update ////////////////////////////

//    public void batchUpdate() {
//        List<Reader> readers = create1000Reader();
//
//        jdbcTemplate.batchUpdate("INSERT INTO Reader VALUES(?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setLong(1, readers.get(i).getId());
//                ps.setString(2, readers.get(i).getSurname());
//                ps.setString(3, readers.get(i).getName());
//                ps.setString(4, readers.get(i).getEmail());
//                ps.setString(5, readers.get(i).getPhoneNumber());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return readers.size();
//            }
//        });
//
//    }
//
//    private List<Reader> create1000Reader() {
//        List<Reader>  readers = new ArrayList<>();
//
//        for (int i = 0; i < 1000; i++) {
//            readers.add(new Reader(i, "Surname" + i, "Name" + i, "test" + i + "@test.com", "Number" + i));
//        }
//
//        return readers;
//    }




}
