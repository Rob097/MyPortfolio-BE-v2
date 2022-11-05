package com.myprojects.myportfolio.core.experience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

    @Query("SELECT DISTINCT EX.id FROM Experience EX WHERE EX.user.id = ?1")
    Optional<List<Integer>> findAllIdsByUserId(Integer userId);

}
