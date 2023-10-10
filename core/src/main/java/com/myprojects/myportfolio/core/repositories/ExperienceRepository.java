package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.Experience;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "ExperienceRepository")
public interface ExperienceRepository extends BaseRepository<Experience, Integer> {

    @Override
    Optional<Experience> findBySlug(String slug);

    @Override
    @Query("SELECT 1 FROM Experience e WHERE e.slug = :#{#slug} AND e.user.id = :#{#experience.user.id}")
    Optional<Experience> findBySlugConstraint(@Param("slug") String slug, @Param("experience") Object experience);
}
