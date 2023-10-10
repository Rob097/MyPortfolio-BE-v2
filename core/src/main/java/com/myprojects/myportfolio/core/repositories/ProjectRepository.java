package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.NewProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "newProjectRepository")
public interface ProjectRepository extends BaseRepository<NewProject, Integer> {

    @Override
    Optional<NewProject> findBySlug(String slug);

    @Override
    @Query("SELECT 1 FROM NewProject p WHERE p.slug = :#{#slug} AND p.user.id = :#{#project.user.id}")
    Optional<NewProject> findBySlugConstraint(@Param("slug") String slug, @Param("project") Object project);

}
