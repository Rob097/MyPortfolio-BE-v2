package com.myprojects.myportfolio.auth.repository;

import com.myprojects.myportfolio.auth.dao.DBRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<DBRole, Integer> {

    DBRole findByName(String name);

}
