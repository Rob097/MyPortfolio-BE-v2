package com.myprojects.myportfolio.clients.user;

import com.myprojects.myportfolio.clients.diary.DiaryR;
import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.experience.ExperienceR;
import com.myprojects.myportfolio.clients.project.ProjectR;
import com.myprojects.myportfolio.clients.skill.UserSkillR;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserR {

    private Integer id;
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
    private DiaryR diary;
    private List<ProjectR> projects;
    private List<UserSkillR> skills;
    private List<EducationR> educationList;
    private List<ExperienceR> experienceList;

}
