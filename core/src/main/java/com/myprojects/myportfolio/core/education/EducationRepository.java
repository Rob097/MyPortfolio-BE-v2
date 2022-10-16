package com.myprojects.myportfolio.core.education;

import com.myprojects.myportfolio.core.project.Project;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Integer> {

    @Query("SELECT e FROM Education e WHERE e.user = ?1")
    List<Project> findAllByUser(User user);

}
