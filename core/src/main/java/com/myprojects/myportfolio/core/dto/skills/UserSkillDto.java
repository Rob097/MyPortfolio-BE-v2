package com.myprojects.myportfolio.core.dto.skills;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myprojects.myportfolio.core.dao.skills.UserSkillPK;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserSkillDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 600632152770892402L;

    SkillDto skill;
    Integer userId;
    Boolean isMain;
    Integer orderId;

    @JsonIgnore
    public UserSkillPK getId() {
        if(userId==null || skill==null || skill.getId()==null)
            return null;
        return new UserSkillPK(userId, skill.getId());
    }

}