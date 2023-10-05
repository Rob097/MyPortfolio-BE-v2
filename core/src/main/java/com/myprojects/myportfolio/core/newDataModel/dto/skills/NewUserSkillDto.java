package com.myprojects.myportfolio.core.newDataModel.dto.skills;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewUserSkillPK;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class NewUserSkillDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 600632152770892402L;

    NewSkillDto skill;
    Integer userId;
    Boolean isMain;
    Integer orderId;

    @JsonIgnore
    public NewUserSkillPK getId() {
        if(userId==null || skill==null || skill.getId()==null)
            return null;
        return new NewUserSkillPK(userId, skill.getId());
    }

}