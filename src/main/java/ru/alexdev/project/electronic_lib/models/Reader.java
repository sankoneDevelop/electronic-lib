package ru.alexdev.project.electronic_lib.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Reader")
public class Reader {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Фамилия не может быть пуста")
    @Size(min = 2, max = 100, message = "Длина фамилии должна быть в диапазоне от 2 до 100 символов")
    @Pattern(regexp = "^[\\p{L}\\s-]+$", message = "Фамилия не должна содержать цифр и специальных символов")
    @Column(name = "surname")
    private String surname;

    @NotEmpty(message = "Имя не может быть пустое")
    @Size(min = 2, max = 100, message = "Длина имени должна@ быть в диапазоне от 2 до 100 символов")
    @Pattern(regexp = "^[\\p{L}\\s-]+$", message = "Имя не должно содержать цифр и специальных символов")
    @Column(name = "name")
    private String name;

    @Email
    @NotEmpty(message = "Почта не может быть пустой")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Номер телефона не может быть пустой")
    @Size(min = 11, max = 11, message = "Номер телефона должен состоять из 11 цифр")
    @Pattern(regexp = "^[0-9]+$", message = "Номер телефона должен содержать только цифры")
    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(mappedBy = "readers")
    private List<Book> books;

    @OneToMany(mappedBy = "reader")
    private List<ReadingSession> readingSessions;

    @OneToMany(mappedBy = "reader")
    private List<Logs> logs;

    public Reader() {
    }

    public Reader(String surname, String name, String email, String phoneNumber) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<ReadingSession> getReadingSessions() {
        return readingSessions;
    }

    public void setReadingSessions(List<ReadingSession> readingSessions) {
        this.readingSessions = readingSessions;
    }

    public List<Logs> getLogs() {
        return logs;
    }

    public void setLogs(List<Logs> logs) {
        this.logs = logs;
    }

}
