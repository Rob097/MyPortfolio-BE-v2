package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.NewUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "newUserRepository")
public interface UserRepository extends BaseRepository<NewUser, Integer> {

    Optional<NewUser> findByEmail(String email);

    @Override
    Optional<NewUser> findBySlug(String slug);

    @Query("SELECT slug from NewUser")
    Optional<List<String>> findAllSlugs();

    @Override
    @Query("SELECT 1 FROM NewUser u WHERE u.slug= ?1 ")
    Optional<NewUser> findBySlugConstraint(String slug, Object user);

}
