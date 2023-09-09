package com.myprojects.myportfolio.core.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query("SELECT new User(U.id, U.email) FROM User U WHERE U.email = ?1")
//    @Cacheable(value = "users", key="#p0", condition="#p0!=null")
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.slug = ?1")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Optional<User> findBySlug(String slug);

    @Query("SELECT slug from User")
    List<String> findAllSlugs();
}
