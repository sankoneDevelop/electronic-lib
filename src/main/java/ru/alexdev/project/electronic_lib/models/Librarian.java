package ru.alexdev.project.electronic_lib.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Librarian")
public class Librarian {

    @Id
    @Column(name = "id")
    private int id;

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

    @NotEmpty(message = "Номер телефона не может быть пустой")
    @Size(min = 11, max = 11, message = "Номер телефона должен состоять из 11 цифр")
    @Pattern(regexp = "^[0-9]+$", message = "Номер телефона должен содержать только цифры")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Email
    @NotEmpty(message = "Почта не может быть пустой")
    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "librarian")
    private AuthUser authUser;

    public Librarian() {
    }

    public Librarian(String surname, String name, String phoneNumber, String email) {
        this.surname = surname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", authUser=" + authUser +
                '}';
    }
}
