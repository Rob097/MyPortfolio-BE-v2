package com.myprojects.myportfolio.core.repositories.skills;

import com.myprojects.myportfolio.core.dao.skills.UserSkill;
import com.myprojects.myportfolio.core.dao.skills.UserSkillPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository(value = "UserSkillRepository")
public interface UserSkillRepository extends JpaRepository<UserSkill, UserSkillPK>, JpaSpecificationExecutor<UserSkill> {
}
