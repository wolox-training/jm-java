package com.wolox.training.models;

import static com.google.common.base.Preconditions.checkNotNull;

import com.wolox.training.exceptions.BookAlreadyOwnedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    public User() {
    }

    public User(String username, String name, LocalDate birthDate) {
        setUsername(username);
        setName(name);
        setBirthDate(birthDate);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<Book> books = new ArrayList<>();

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = checkNotNull(username);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = checkNotNull(name);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final LocalDate birthDate) { this.birthDate = checkNotNull(birthDate); }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public void setBooks(final List<Book> books) {
        for (Book book : books) {
            addBook(book);
        }
    }

    public void addBook(Book book) {
        checkAlreadyOwned(book);
        this.books.add(book);
    }

    private void checkAlreadyOwned(final Book book){
        if (this.books.contains(book)){
            throw new BookAlreadyOwnedException();
        }
    }
}
