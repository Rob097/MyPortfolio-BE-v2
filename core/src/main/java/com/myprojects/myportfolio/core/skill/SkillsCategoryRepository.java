package com.myprojects.myportfolio.core.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsCategoryRepository extends JpaRepository<SkillsCategory, Integer>, JpaSpecificationExecutor<SkillsCategory> {
}
