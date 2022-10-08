package com.myprojects.myportfolio.auth.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IApplicationUserRepository extends JpaRepository<DBUser, Integer> {

    DBUser findByEmail(String email);

}
