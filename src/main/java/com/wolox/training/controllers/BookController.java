package com.wolox.training.controllers;

import com.wolox.training.exceptions.BookIdMismatchException;
import com.wolox.training.exceptions.BookNotFoundException;
import com.wolox.training.models.Book;
import com.wolox.training.repositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/greeting")
    public ModelAndView greeting (@RequestParam(name="name", required=false, defaultValue="Stranger") final String name, Model model) {
        model.addAttribute("name", name);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting");
        return modelAndView;
    }

    @GetMapping
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable final Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody final Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Book updateBook(@RequestBody final Book book, @PathVariable final Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }

    @GetMapping("/author/{bookAuthor}")
    public Book findTopByAuthor(@PathVariable final String bookAuthor) {
        return bookRepository.findTopByAuthor(bookAuthor).orElseThrow(BookNotFoundException::new);
    }

}
