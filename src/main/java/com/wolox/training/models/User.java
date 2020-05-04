package com.wolox.training.models;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.wolox.training.constants.ExceptionMessages.BLANK_VALUE;
import static com.wolox.training.constants.ExceptionMessages.INVALID_BIRTHDATE;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
    @JsonManagedReference
    @JoinTable(name = "users_books",
        joinColumns = @JoinColumn(name = "books_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "users_id",
            referencedColumnName = "id"))
    private List<Book> books = new ArrayList<>();

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        checkArgument(username.length() > 0, BLANK_VALUE, "username");
        this.username = checkNotNull(username);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        checkArgument(name.length() > 0, BLANK_VALUE, "name");
        this.name = checkNotNull(name);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final LocalDate birthDate) {
        checkArgument(birthDate.isBefore(LocalDate.now()), INVALID_BIRTHDATE);
        this.birthDate = checkNotNull(birthDate);
    }

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
