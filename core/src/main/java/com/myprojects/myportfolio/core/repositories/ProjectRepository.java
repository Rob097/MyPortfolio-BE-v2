package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "ProjectRepository")
public interface ProjectRepository extends BaseRepository<Project, Integer>, UserRelatedRepository<Project> {

    @Override
    Optional<Project> findBySlug(String slug);

    @Override
    @Query("SELECT 1 FROM Project p WHERE p.slug = :#{#slug} AND p.user.id = :#{#project.user.id}")
    Optional<Project> findBySlugConstraint(@Param("slug") String slug, @Param("project") Object project);

    @Override
    @Query("SELECT p.slug FROM Project p WHERE p.user.id = :#{#userId}")
    Optional<List<String>> findSlugsByUserId(Integer userId);

}
