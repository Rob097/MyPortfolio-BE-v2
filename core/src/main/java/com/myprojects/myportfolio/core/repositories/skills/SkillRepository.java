package com.myprojects.myportfolio.core.repositories.skills;

import com.myprojects.myportfolio.core.dao.skills.Skill;
import com.myprojects.myportfolio.core.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "SkillRepository")
public interface SkillRepository extends BaseRepository<Skill, Integer> {
}
