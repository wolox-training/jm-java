package com.wolox.training.repositories;

import com.wolox.training.models.Book;
import java.util.Optional;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    @UniqueElements
    Optional<Book> findTopByAuthor(final String author);

}
