package com.wolox.training.controllers;


import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolox.training.models.Book;
import com.wolox.training.models.User;
import com.wolox.training.repositories.BookRepository;
import com.wolox.training.repositories.UserRepository;
import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)


class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private User user;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        user = new User("jhon_doe", "JhonDoe", LocalDate.parse("1990-01-01"));
    }

    @Test
    public void givenUsers_whenGetUsers_thenReturnsJsonArray() throws Exception {

        List<User> allUsers = Collections.singletonList(user);

        given(userRepository.findAll()).willReturn(allUsers);

        mockMvc.perform(get(URI.create("/api/users"))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].name", is(user.getName())));
    }

    @Test
    public void givenUsers_whenGetUser_thenReturnsJson() throws Exception {

        given(userRepository.findById(user.getId())).willReturn(ofNullable(user));

        mockMvc.perform(get(URI.create(String.format("/api/users/%d", user.getId())))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(user.getName())));
    }

    @Test
    public void givenValidUser_whenPostUser_thenReturnsJson() throws Exception {

        given(userRepository.save(user)).willReturn(user);

        mockMvc.perform(post(URI.create("/api/users"))
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void givenInvalidUser_whenPostUser_thenReturnsBadRequest() throws Exception {
        user = new User();
        user.setName("Jane Doe");
        user.setUsername("jane_doe");
        given(userRepository.save(user)).willReturn(user);

        mockMvc.perform(post(URI.create("/api/users"))
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidUser_whenDeletesUser_thenReturnsNoContent() throws Exception {

        given(userRepository.findById(user.getId())).willReturn(ofNullable(user));

        mockMvc.perform(delete(URI.create(String.format("/api/users/%d", user.getId())))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void givenValidUser_whenUpdatesUser_thenReturnsAccepted() throws Exception {
        given(userRepository.findById(user.getId())).willReturn(ofNullable(user));

        String new_name = "Jane Doe";
        user.setName(new_name);

        mockMvc.perform(put(URI.create(String.format("/api/users/%d", user.getId())))
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());
    }

    @Test
    public void givenAuthor_whenFindTopByName_thenReturnsJson() throws Exception {

        given(userRepository.findTopByName(user.getName())).willReturn(ofNullable(user));

        mockMvc.perform(get(URI.create(String.format("/api/users/name/%s", user.getName())))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(user.getName())));
    }

    @Test
    public void givenValidUser_whenAddBooks_thenReturnsAccepted() throws Exception {

        Book book = new Book("Terror", "Stephen King", "The Mist", "no value",
            "SOME PUBLISHER", "2000", 123, "ISBN_CODE", null);

        List<Book> new_book = Collections.singletonList(book);

        given(userRepository.findById(user.getId())).willReturn(ofNullable(user));
        given(bookRepository.findById(user.getId())).willReturn(of(book));

        mockMvc.perform(put(URI.create(String.format("/api/users/%d/books", user.getId())))
            .content(objectMapper.writeValueAsString(new_book))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());
    }

    @Test
    public void givenValidUser_whenDeleteBooks_thenReturnsNoContent() throws Exception {
        List<User> current_user = Collections.singletonList(user);

        Book book = new Book("Terror", "Stephen King", "The Mist", "no value",
            "SOME PUBLISHER", "2000", 123, "ISBN_CODE", current_user);

        given(userRepository.findById(user.getId())).willReturn(ofNullable(user));
        given(bookRepository.findById(user.getId())).willReturn(of(book));

        mockMvc.perform(delete(URI.create(String.format("/api/users/%d/books/%d", user.getId(), book.getId())))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
}