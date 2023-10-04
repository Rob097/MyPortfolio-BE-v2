package com.myprojects.myportfolio.core.newDataModel.dto.skills;

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
}