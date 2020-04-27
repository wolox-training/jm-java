package com.wolox.training.models;

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
        this.genre = genre;
        this.author = author;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
        this.users = users;
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
