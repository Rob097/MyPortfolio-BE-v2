package com.myprojects.myportfolio.clients.skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExperienceSkillR {
    private Integer id;
    private Integer experienceId;
    private SkillR skill;
}
