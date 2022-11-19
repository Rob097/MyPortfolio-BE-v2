package com.myprojects.myportfolio.core.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query("SELECT new User(U.id, U.email) FROM User U WHERE U.email = ?1")
//    @Cacheable(value = "users", key="#p0", condition="#p0!=null")
    User findByEmail(String email);
}
