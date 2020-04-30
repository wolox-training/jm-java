package com.wolox.training.controllers;


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
import com.wolox.training.repositories.BookRepository;
import java.net.URI;
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
@WebMvcTest(BookController.class)


class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private Book book;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        book = new Book("Terror", "StephenKing", "The Mist", "no value",
            "SOME PUBLISHER", "2000", 123, "ISBN_CODE", null);
    }

    @Test
    public void givenBooks_whenGetBooks_thenReturnsJsonArray() throws Exception {

        List<Book> allBooks = Collections.singletonList(book);

        given(bookRepository.findAll()).willReturn(allBooks);

        mockMvc.perform(get(URI.create("/api/books"))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].author", is(book.getAuthor())));
    }

    @Test
    public void givenBooks_whenGetBook_thenReturnsJson() throws Exception {

        given(bookRepository.findById(book.getId())).willReturn(ofNullable(book));

        mockMvc.perform(get(URI.create(String.format("/api/books/%d", book.getId())))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.author", is(book.getAuthor())));
    }

    @Test
    public void givenValidBook_whenPostBook_thenReturnsJson() throws Exception {

        given(bookRepository.save(book)).willReturn(book);

        mockMvc.perform(post(URI.create("/api/books"))
            .content(objectMapper.writeValueAsString(book))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void givenInvalidBook_whenPostBook_thenReturnsBadRequest() throws Exception {
        book = new Book();
        given(bookRepository.save(book)).willReturn(book);

        mockMvc.perform(post(URI.create("/api/books"))
            .content(objectMapper.writeValueAsString(book))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidBook_whenDeletesBook_thenReturnsNoContent() throws Exception {

        given(bookRepository.findById(book.getId())).willReturn(ofNullable(book));

        mockMvc.perform(delete(URI.create(String.format("/api/books/%d", book.getId())))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void givenValidBook_whenUpdatesBook_thenReturnsAccepted() throws Exception {
        given(bookRepository.findById(book.getId())).willReturn(ofNullable(book));

        String new_publisher = "Another Publisher";
        book.setPublisher(new_publisher);

        mockMvc.perform(put(URI.create(String.format("/api/books/%d", book.getId())))
            .content(objectMapper.writeValueAsString(book))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());
    }

    @Test
    public void givenAuthor_whenFindTopByAuthor_thenReturnsJson() throws Exception {

        given(bookRepository.findTopByAuthor(book.getAuthor())).willReturn(ofNullable(book));

        mockMvc.perform(get(URI.create(String.format("/api/books/author/%s", book.getAuthor())))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.author", is(book.getAuthor())));
    }
}