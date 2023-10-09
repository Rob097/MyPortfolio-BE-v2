package com.myprojects.myportfolio.repository;

import com.myprojects.myportfolio.dao.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticationUserRepository extends JpaRepository<DBUser, Integer> {

    DBUser findByEmail(String email);

}
