package com.myprojects.myportfolio.core.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {

    Optional<Project> findBySlug(String slug);

    @Query("SELECT slug from Project where user.id = ?1")
    List<String> findAllSlugs(Integer userId);

}
