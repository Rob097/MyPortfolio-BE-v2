package com.myprojects.myportfolio.clients.user;

import com.myprojects.myportfolio.clients.diary.DiaryR;
import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.experience.ExperienceR;
import com.myprojects.myportfolio.clients.project.ProjectR;
import com.myprojects.myportfolio.clients.skill.SkillR;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserR {

    private Integer id;
    private String slug;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String nationality;
    private String nation;
    private String province;
    private String city;
    private String cap;
    private String address;
    private String sex;
    private List<DiaryR> diaries;
    private List<ProjectR> projects;
    private List<SkillR> skills;
    private List<EducationR> educations;
    private List<ExperienceR> experiences;

}
