package com.myprojects.myportfolio.core.services.skills;

import com.myprojects.myportfolio.core.dao.skills.UserSkill;
import com.myprojects.myportfolio.core.dao.skills.UserSkillPK;

public interface UserSkillServiceI {

    UserSkill findById(UserSkillPK id);

    UserSkill save(UserSkill t);

    UserSkill update(UserSkill t);

    void delete(UserSkill t);

}
