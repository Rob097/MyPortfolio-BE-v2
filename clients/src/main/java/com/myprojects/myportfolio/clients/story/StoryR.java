package com.myprojects.myportfolio.clients.story;

import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.experience.ExperienceR;
import com.myprojects.myportfolio.clients.skill.SkillR;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoryR {

    private Integer id;
    private LocalDateTime entryDateTime;
    private String title;
    private String description;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Boolean isPrimaryStory;
    private Integer diaryId;
    private Set<EducationR> educationList;
    private Set<ExperienceR> experienceList;
    private Set<SkillR> skillList;

}
