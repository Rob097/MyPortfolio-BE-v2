package com.myprojects.myportfolio.core.newDataModel.repositories;

import com.myprojects.myportfolio.core.newDataModel.dao.NewExperience;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "newExperienceRepository")
public interface ExperienceRepository extends BaseRepository<NewExperience, Integer> {

    @Override
    Optional<NewExperience> findBySlug(String slug);

    @Override
    @Query("SELECT 1 FROM NewExperience e WHERE e.slug = :#{#slug} AND e.user.id = :#{#experience.user.id}")
    Optional<NewExperience> findBySlugConstraint(@Param("slug") String slug, @Param("experience") Object experience);
}
