package ru.alexdev.project.electronic_lib.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.models.Reader;
import ru.alexdev.project.electronic_lib.repositories.ReaderRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ReaderService {

    private final ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public Reader findOne(int id) {
        return readerRepository.findById(id).orElse(null);
    }

    public void save(Reader reader) {
        readerRepository.save(reader);
    }

    public void update(int id, Reader updatedReader) {
        updatedReader.setId(id);
        readerRepository.save(updatedReader);
    }

    public void delete(int id) {
        readerRepository.deleteById(id);
    }

}
