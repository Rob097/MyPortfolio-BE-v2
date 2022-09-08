package com.myprojects.myportfolio.core.project;

import com.myprojects.myportfolio.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("SELECT p FROM Project p WHERE p.owner = ?1")
    List<Project> findAllByOwner(User owner);

}
