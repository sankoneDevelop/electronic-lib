package ru.alexdev.project.electronic_lib.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexdev.project.electronic_lib.models.Book;
import ru.alexdev.project.electronic_lib.models.Comment;
import ru.alexdev.project.electronic_lib.models.Reader;
import ru.alexdev.project.electronic_lib.repositories.BookRepository;
import ru.alexdev.project.electronic_lib.repositories.CommentRepository;
import ru.alexdev.project.electronic_lib.repositories.ReaderRepository;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, BookRepository bookRepository, ReaderRepository readerRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findOne(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public void update(int id, Comment comment) {
        comment.setId(id);
        commentRepository.save(comment);
    }

    public void delete(int id) {
        commentRepository.deleteById(id);
    }

}
