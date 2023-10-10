package com.myprojects.myportfolio.core.repositories.skills;

import com.myprojects.myportfolio.core.dao.skills.NewSkill;
import com.myprojects.myportfolio.core.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "newSkillRepository")
public interface SkillRepository extends BaseRepository<NewSkill, Integer> {
}
