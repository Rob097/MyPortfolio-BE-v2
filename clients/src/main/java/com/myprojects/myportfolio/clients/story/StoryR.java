package com.myprojects.myportfolio.clients.story;

import com.myprojects.myportfolio.clients.diary.DiaryR;
import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.experience.ExperienceR;
import com.myprojects.myportfolio.clients.skill.SkillR;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StoryR {

    private Integer id;
    private String slug;
    private LocalDateTime entryDateTime;
    private String title;
    private String description;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Boolean isPrimaryStory;
    private DiaryR diary;
    private Set<EducationR> educations;
    private Set<ExperienceR> experiences;
    private Set<SkillR> skills;

}
