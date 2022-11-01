package com.myprojects.myportfolio.core.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT USR_SK.skill_id FROM user_skills USR_SK WHERE USR_SK.user_id = ?1")
    Optional<Set<Integer>> findAllIdsByUserId(Integer userId);

    @Query(nativeQuery = true, value = "SELECT DISTINCT SK.* FROM skills SK inner join user_skills USR_SK ON SK.id = USR_SK.skill_id WHERE USR_SK.user_id = ?1")
    Optional<Set<Skill>> findAllByUserId(Integer userId);

}
