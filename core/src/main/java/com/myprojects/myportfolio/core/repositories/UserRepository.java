package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "UserRepository")
public interface UserRepository extends BaseRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Override
    Optional<User> findBySlug(String slug);

    @Query("SELECT slug from User")
    Optional<List<String>> findAllSlugs();

    @Override
    @Query("SELECT 1 FROM User u WHERE u.slug= ?1 ")
    Optional<User> findBySlugConstraint(String slug, Object user);

}
