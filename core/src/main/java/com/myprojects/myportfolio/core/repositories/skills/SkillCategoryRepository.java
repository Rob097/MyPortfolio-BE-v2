package com.myprojects.myportfolio.core.repositories.skills;

import com.myprojects.myportfolio.core.dao.skills.NewSkillCategory;
import com.myprojects.myportfolio.core.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "newSkillCategoryRepository")
public interface SkillCategoryRepository extends BaseRepository<NewSkillCategory, Integer> {
}
