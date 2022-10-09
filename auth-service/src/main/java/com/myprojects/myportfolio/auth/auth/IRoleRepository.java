package com.myprojects.myportfolio.auth.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<DBRole, Integer> {

    DBRole findByName(String name);

}
