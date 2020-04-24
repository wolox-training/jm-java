package com.wolox.training.controllers;

import com.wolox.training.exceptions.UserIdMismatchException;
import com.wolox.training.exceptions.UserNotFoundException;
import com.wolox.training.models.User;
import com.wolox.training.repositories.UserRepository;
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

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public User findTopByAuthor(@PathVariable final String name) {
        return userRepository.findTopByName(name).orElseThrow(UserNotFoundException::new);
    }

}
