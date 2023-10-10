package com.myprojects.myportfolio.core.repositories.skills;

import com.myprojects.myportfolio.core.dao.skills.SkillCategory;
import com.myprojects.myportfolio.core.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "SkillCategoryRepository")
public interface SkillCategoryRepository extends BaseRepository<SkillCategory, Integer> {
}
