package com.wolox.training.repositories;

import com.wolox.training.models.Book;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface BookRepository extends Repository<Book, Long> {

    Optional<Book> findByAuthor(String author);

}
