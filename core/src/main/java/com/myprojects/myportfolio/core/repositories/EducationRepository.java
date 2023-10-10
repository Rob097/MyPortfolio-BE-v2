package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.Education;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "EducationRepository")
public interface EducationRepository extends BaseRepository<Education, Integer> {

    @Override
    Optional<Education> findBySlug(String slug);

    @Override
    @Query("SELECT 1 FROM Education e WHERE e.slug = :#{#slug} AND e.user.id = :#{#education.user.id}")
    Optional<Education> findBySlugConstraint(@Param("slug") String slug, @Param("education") Object education);

}
