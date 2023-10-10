package com.myprojects.myportfolio.core.services.skills;

import com.myprojects.myportfolio.core.dao.skills.NewUserSkill;
import com.myprojects.myportfolio.core.dao.skills.NewUserSkillPK;

public interface UserSkillServiceI {

    NewUserSkill findById(NewUserSkillPK id);

    NewUserSkill save(NewUserSkill t);

    NewUserSkill update(NewUserSkill t);

    void delete(NewUserSkill t);

}
