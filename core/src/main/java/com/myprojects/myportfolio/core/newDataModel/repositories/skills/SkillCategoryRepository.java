package com.myprojects.myportfolio.core.newDataModel.repositories.skills;

import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewSkillCategory;
import com.myprojects.myportfolio.core.newDataModel.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "newSkillCategoryRepository")
public interface SkillCategoryRepository extends BaseRepository<NewSkillCategory, Integer> {
}
