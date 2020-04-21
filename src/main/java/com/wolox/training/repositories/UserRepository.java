package com.wolox.training.repositories;

import com.wolox.training.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Book, Long> {


}
