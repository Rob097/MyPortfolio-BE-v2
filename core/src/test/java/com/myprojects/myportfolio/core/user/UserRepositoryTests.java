package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.clients.user.UserQ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User userJohn;
    private User userTom;


    @BeforeEach
    public void init() {
        userJohn = new User();
        userJohn.setFirstName("John");
        userJohn.setLastName("Doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        userRepository.save(userJohn);

        userTom = new User();
        userTom.setFirstName("Tom");
        userTom.setLastName("Doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        userRepository.save(userTom);
    }

    @DisplayName("JUnit test does user by last name exists")
    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        UserSpecification spec =
                new UserSpecification(new UserQ("lastName", ":", "doe"));

        List<User> results = userRepository.findAll(spec);

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isIn(results);

    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        UserSpecification spec1 =
                new UserSpecification(new UserQ("firstName", ":", "john"));
        UserSpecification spec2 =
                new UserSpecification(new UserQ("lastName", ":", "doe"));

        List<User> results = userRepository.findAll(Specification.where(spec1).and(spec2));

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isNotIn(results);
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        UserSpecification spec1 =
                new UserSpecification(new UserQ("age", ">", "25"));
        UserSpecification spec2 =
                new UserSpecification(new UserQ("lastName", ":", "doe"));

        List<User> results =
                userRepository.findAll(Specification.where(spec1).and(spec2));

        assertThat(userTom).isIn(results);
        assertThat(userJohn).isNotIn(results);
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        UserSpecification spec1 =
                new UserSpecification(new UserQ("firstName", ":", "Adam"));
        UserSpecification spec2 =
                new UserSpecification(new UserQ("lastName", ":", "Fox"));

        List<User> results =
                userRepository.findAll(Specification.where(spec1).and(spec2));

        assertThat(userJohn).isNotIn(results);
        assertThat(userTom).isNotIn(results);
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        UserSpecification spec =
                new UserSpecification(new UserQ("firstName", ":", "jo"));

        List<User> results = userRepository.findAll(spec);

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isNotIn(results);
    }

}
