package com.myprojects.myportfolio.core.newDataModel.repositories;

import com.myprojects.myportfolio.core.newDataModel.dao.NewEducation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "newEducationRepository")
public interface EducationRepository extends BaseRepository<NewEducation, Integer> {

    @Override
    Optional<NewEducation> findBySlug(String slug);

    @Override
    @Query("SELECT 1 FROM NewEducation e WHERE e.slug = :#{#slug} AND e.user.id = :#{#education.user.id}")
    Optional<NewEducation> findBySlugConstraint(@Param("slug") String slug, @Param("education") Object education);

}
