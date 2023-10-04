package com.myprojects.myportfolio.core.newDataModel.repositories.skills;

import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewSkill;
import com.myprojects.myportfolio.core.newDataModel.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "newSkillRepository")
public interface SkillRepository extends BaseRepository<NewSkill, Integer> {
}
