package com.myprojects.myportfolio.clients.user;

import com.myprojects.myportfolio.clients.diary.DiaryR;
import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.experience.ExperienceR;
import com.myprojects.myportfolio.clients.general.AddressR;
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class UserSkillR {
        private Integer userId;
        private SkillR skill;
        private Boolean isMain;
        private Integer orderId;
    }

    private Integer id;
    private String slug;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer age;
    private String nationality;
    private AddressR address;
    private String sex;
    private String title;
    private String description;
    private List<DiaryR> diaries;
    private List<ProjectR> projects;
    private List<UserSkillR> skills;
    private List<EducationR> educations;
    private List<ExperienceR> experiences;

}
