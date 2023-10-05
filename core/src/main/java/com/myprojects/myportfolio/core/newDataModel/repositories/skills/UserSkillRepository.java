package com.myprojects.myportfolio.core.newDataModel.repositories.skills;

import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewUserSkill;
import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewUserSkillPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository(value = "newUserSkillRepository")
public interface UserSkillRepository extends JpaRepository<NewUserSkill, NewUserSkillPK>, JpaSpecificationExecutor<NewUserSkill> {
}
