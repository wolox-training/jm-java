package com.wolox.training.controllers;

import com.wolox.training.exceptions.BookNotFoundException;
import com.wolox.training.exceptions.UserAlreadyExistsException;
import com.wolox.training.exceptions.UserIdMismatchException;
import com.wolox.training.exceptions.UserNotFoundException;
import com.wolox.training.models.Book;
import com.wolox.training.models.User;
import com.wolox.training.repositories.BookRepository;
import com.wolox.training.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable final Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody final User user) {
        final Optional<User> username = userRepository.findTopByUsername(user.getUsername());
        username.ifPresent(value -> {
            throw new UserAlreadyExistsException();
        });
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@RequestBody final User user, @PathVariable final Long id) {
        if (user.getId() != id) {
            throw new UserIdMismatchException();
        }
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }

    @GetMapping("/name/{name}")
    public User findTopByName(@PathVariable final String name) {
        return userRepository.findTopByName(name).orElseThrow(UserNotFoundException::new);
    }

    @PutMapping("/{id}/books")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addBooks( @PathVariable final Long id, @RequestBody final List<Book> books){
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setBooks(books);
        userRepository.save(user);
    }

    @DeleteMapping("/{id}/books/{book_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable final Long id, @PathVariable final Long book_id){
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        bookRepository.findById(book_id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(book_id);
    }
}
