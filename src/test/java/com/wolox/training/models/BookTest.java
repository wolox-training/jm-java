package com.wolox.training.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wolox.training.repositories.BookRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
class BookTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @MockBean
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("Terror", "Stephen King", "The Mist", "no value",
            "SOME PUBLISHER", "2000", 123, "ISBN_CODE", null);
    }


    @Test
    public void whenFindTopByAuthor_thenReturnBook() {
        entityManager.persist(book);
        entityManager.flush();
        Optional<Book> bookFound = bookRepository.findTopByAuthor(book.getAuthor());
        assertThat(bookFound.get()).isEqualToComparingFieldByField(book);
    }

    @Test
    public void whenNoAuthor_throwsException() {
        assertThrows(NullPointerException.class, () -> book.setAuthor(null));
    }

    @Test
    public void whenNoTitle_throwsException() {
        assertThrows(NullPointerException.class, () -> book.setTitle(null));
    }

    @Test
    public void whenNoSubtitle_throwsException() {
        assertThrows(NullPointerException.class, () -> book.setSubtitle(null));
    }

    @Test
    public void whenNoPublisher_throwsException() {
        assertThrows(NullPointerException.class, () -> book.setPublisher(null));
    }

    @Test
    public void whenNoPages_throwsException() {
        assertThrows(NullPointerException.class, () -> book.setPages(null));
    }

    @Test
    public void whenZeroPages_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> book.setPages(0));
    }

}