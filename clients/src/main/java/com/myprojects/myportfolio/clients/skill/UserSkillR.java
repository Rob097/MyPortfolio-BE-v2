package com.myprojects.myportfolio.clients.skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSkillR {
    private Integer id;
    private Integer userId;
    private SkillR skill;
}
