package com.myprojects.myportfolio.core.newDataModel.repositories;

import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value="newUserRepository")
public interface UserRepository extends BaseRepository<NewUser, Integer> {

    Optional<NewUser> findByEmail(String email);

    Optional<NewUser> findBySlug(String slug);

    @Query("SELECT slug from NewUser")
    Optional<List<String>> findAllSlugs();

}
