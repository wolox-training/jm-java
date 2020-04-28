package com.wolox.training.models;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.wolox.training.constants.ExceptionMessages.GREATER_THAN_ZERO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@ApiModel(description = "Library Books")
public class Book {

    public Book() {
    }

    public Book(String genre, String author, String title, String subtitle, String publisher,
        String year, Integer pages, String isbn, List<User> users) {
        setGenre(genre);
        setAuthor(author);
        setTitle(title);
        setSubtitle(subtitle);
        setPublisher(publisher);
        setYear(year);
        setPages(pages);
        setIsbn(isbn);
        setUsers(users);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private long id;

    @Column
    @ApiModelProperty(notes = "The book genre, could be Horror, Comedy, Drama, etc.")
    private String genre;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Book's Author name")
    private String author;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Book's tittle")
    private String title;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Book's subtitle")
    private String subtitle;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Book's publisher")
    private String publisher;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Book's year of publication")
    private String year;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Book's number of pages")
    private Integer pages;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Book's International Standard Book Number")
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) { this.author = checkNotNull(author); }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = checkNotNull(title);
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = checkNotNull(subtitle);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = checkNotNull(publisher);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = checkNotNull(year);
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        checkArgument(pages > 0, GREATER_THAN_ZERO, "pages");
        this.pages = checkNotNull(pages);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = checkNotNull(isbn);
    }
}
