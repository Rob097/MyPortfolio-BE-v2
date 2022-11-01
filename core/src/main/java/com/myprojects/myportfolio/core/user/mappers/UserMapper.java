package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.diary.mappers.DiaryMapper;
import com.myprojects.myportfolio.core.education.mappers.EducationMapper;
import com.myprojects.myportfolio.core.experience.mappers.ExperienceMapper;
import com.myprojects.myportfolio.core.project.mappers.ProjectMapper;
import com.myprojects.myportfolio.core.skill.mappers.SkillMapper;
import com.myprojects.myportfolio.core.user.User;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserR> {

    @Autowired
    private DiaryMapper diaryMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EducationMapper educationMapper;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private SkillMapper skillMapper;

    @Override
    public User map(UserR input){
        return this.map(input, new User());
    }

    @Override
    public User map(UserR input, User output) {
        if (input == null) {
            return null;
        }
        if (output == null) {
            output = new User();
        }

        output.setId(input.getId());
        output.setFirstName(input.getFirstName());
        output.setLastName(input.getLastName());
        output.setEmail(input.getEmail());
        output.setAge(input.getAge());
        output.setNationality(input.getNationality());
        output.setNation(input.getNation());
        output.setProvince(input.getProvince());
        output.setCity(input.getCity());
        output.setCap(input.getCap());
        output.setAddress(input.getAddress());
        if(!Strings.isNullOrEmpty(input.getSex())) {
            output.setSex(input.getSex().equals("MALE") ? User.Sex.MALE : input.getSex().equals("FEMALE") ? User.Sex.FEMALE : null);
        }
        if(input.getDiary()!=null){
            output.setDiary(this.diaryMapper.map(input.getDiary()));
        }
        if(input.getProjects()!=null && !input.getProjects().isEmpty()) {
            output.setProjects(input.getProjects().stream().map(el -> this.projectMapper.map(el)).collect(Collectors.toList()));
        }
        if(input.getSkills()!=null && !input.getSkills().isEmpty()) {
            output.setSkills(input.getSkills().stream().map(el -> this.skillMapper.map(el)).collect(Collectors.toSet()));
        }
        if(input.getEducationList()!=null && !input.getEducationList().isEmpty()) {
            output.setEducations(input.getEducationList().stream().map(el -> this.educationMapper.map(el)).collect(Collectors.toList()));
        }
        if(input.getExperienceList()!=null && !input.getExperienceList().isEmpty()) {
            output.setExperiences(input.getExperienceList().stream().map(el -> this.experienceMapper.map(el)).collect(Collectors.toList()));
        }

        return output;

    }

}
