package com.myprojects.myportfolio.clients.experience;

import com.myprojects.myportfolio.clients.skill.SkillR;
import com.myprojects.myportfolio.clients.user.UserR;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExperienceR {

    private Integer id;
    private UserR user;
    private String title;
    private String employmentType;
    private String companyName;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Set<SkillR> skills;

}
