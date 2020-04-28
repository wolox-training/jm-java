package com.wolox.training.repositories;

import com.wolox.training.models.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findTopByName(final String name);

    Optional<User> findTopByUsername(final String name);

}
