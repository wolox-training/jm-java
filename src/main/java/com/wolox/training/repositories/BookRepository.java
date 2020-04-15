package com.wolox.training.repositories;

import com.wolox.training.models.Book;
import org.springframework.data.repository.Repository;

public interface BookRepository extends Repository<Book, Long> {

    Book findByAuthor(String author);

}
