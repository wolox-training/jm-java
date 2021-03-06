package com.wolox.training.repositories;

import com.wolox.training.models.Book;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findTopByAuthor(final String author);

}
