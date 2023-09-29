package com.myprojects.myportfolio.core.newDataModel.repositories;

import com.myprojects.myportfolio.core.newDataModel.dao.NewEducation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "newEducationRepository")
public interface EducationRepository extends BaseRepository<NewEducation, Integer> {

    @Override
    Optional<NewEducation> findBySlug(String slug);
}
