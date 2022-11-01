package com.myprojects.myportfolio.core.project;

import com.myprojects.myportfolio.core.education.EducationProjection;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("SELECT DISTINCT PR.id FROM Project PR WHERE PR.user.id = ?1")
    Optional<List<Integer>> findAllIdsByUserId(Integer userId);

    Optional<List<ProjectProjection>> findAllByUserId(Integer userId);

}
