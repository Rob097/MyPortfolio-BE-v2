package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.clients.general.specifications.SpecificationsBuilder;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Specification<User> spec = this.defineFiltersAndStoreView("lastName:doe");

        List<User> results = userRepository.findAll(spec);

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isIn(results);

    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        Specification<User> spec1 = this.defineFiltersAndStoreView("firstName:john");
        Specification<User> spec2 = this.defineFiltersAndStoreView("lastName:doe");

        List<User> results = userRepository.findAll(Specification.where(spec1).and(spec2));

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isNotIn(results);
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        Specification<User> spec1 = this.defineFiltersAndStoreView("age>25");
        Specification<User> spec2 = this.defineFiltersAndStoreView("lastName:doe");

        List<User> results =
                userRepository.findAll(Specification.where(spec1).and(spec2));

        assertThat(userTom).isIn(results);
        assertThat(userJohn).isNotIn(results);
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        Specification<User> spec1 = this.defineFiltersAndStoreView("firstName:Adam");
        Specification<User> spec2 = this.defineFiltersAndStoreView("lastName:Fox");

        List<User> results =
                userRepository.findAll(Specification.where(spec1).and(spec2));

        assertThat(userJohn).isNotIn(results);
        assertThat(userTom).isNotIn(results);
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        Specification<User> spec = this.defineFiltersAndStoreView("firstName:jo");

        List<User> results = userRepository.findAll(spec);

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isNotIn(results);
    }

    private <T> Specification<T> defineFiltersAndStoreView(String filters){
        SpecificationsBuilder builder = new SpecificationsBuilder();
        if(Strings.isNotBlank(filters)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(filters + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }

        return builder.build();
    }

}
