package com.myprojects.myportfolio.core.education;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Integer> {

    @Query("SELECT DISTINCT ED.id FROM Education ED WHERE ED.user.id = ?1")
    Optional<List<Integer>> findAllIdsByUserId(Integer userId);

    Optional<List<EducationProjection>> findAllByUserId(Integer userId);
}
