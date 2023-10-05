package com.myprojects.myportfolio.core.newDataModel.services.skills;

import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewUserSkill;

public interface UserSkillServiceI {

    NewUserSkill save(NewUserSkill t);

    NewUserSkill update(NewUserSkill t);

    void delete(NewUserSkill t);

}
