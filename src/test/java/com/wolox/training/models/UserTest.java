package com.wolox.training.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.wolox.training.repositories.UserRepository;
import java.time.LocalDate;
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
class UserTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("jhon_doe", "Jhon Doe", LocalDate.of(1990,1,1));
    }

    @Test
    public void whenFindTopByName_thenReturnUser() {
        entityManager.persist(user);
        entityManager.flush();
        Optional<User> userFound = userRepository.findTopByName(user.getName());
        assertThat(userFound.get()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenNoName_throwsException() {
        assertThrows(NullPointerException.class, () -> user.setName(null));
    }

    @Test
    public void whenNoUsername_throwsException() {
        assertThrows(NullPointerException.class, () -> user.setUsername(null));
    }

    @Test
    public void whenNoBirthDate_throwsException() {
        assertThrows(NullPointerException.class, () -> user.setBirthDate(null));
    }

    @Test
    public void whenInvalidBirthdate_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> user.setBirthDate(LocalDate.now()));
    }

}