package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.diary.mappers.DiaryRMapper;
import com.myprojects.myportfolio.core.education.mappers.EducationRMapper;
import com.myprojects.myportfolio.core.experience.mappers.ExperienceRMapper;
import com.myprojects.myportfolio.core.project.mappers.ProjectRMapper;
import com.myprojects.myportfolio.core.skill.mappers.UserSkillRMapper;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserRMapper implements Mapper<UserR, User> {

    @Autowired
    private DiaryRMapper diaryRMapper;

    @Autowired
    private ProjectRMapper projectRMapper;

    @Autowired
    private UserSkillRMapper userSkillRMapper;

    @Autowired
    private EducationRMapper educationRMapper;

    @Autowired
    private ExperienceRMapper experienceRMapper;

    @Override
    public UserR map(User input){
        return this.map(input, new UserR());
    }

    @Override
    public UserR map(User input, UserR output){
        if(input==null){
            return null;
        }
        if(output==null){
            output = new UserR();
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
        if(input.getSex()!=null) {
            output.setSex(input.getSex().toString());
        }
        if(input.getDiary()!=null){
            output.setDiary(this.diaryRMapper.map(input.getDiary()));
        }
        if(input.getProjects()!=null && !input.getProjects().isEmpty()) {
            output.setProjects(input.getProjects().stream().map(el -> this.projectRMapper.map(el)).collect(Collectors.toList()));
        }
        if(input.getSkills()!=null && !input.getSkills().isEmpty()) {
            output.setSkills(input.getSkills().stream().map(el -> this.userSkillRMapper.map(el)).collect(Collectors.toList()));
        }
        if(input.getEducationList()!=null && !input.getEducationList().isEmpty()) {
            output.setEducationList(input.getEducationList().stream().map(el -> this.educationRMapper.map(el)).collect(Collectors.toList()));
        }
        if(input.getExperienceList()!=null && !input.getExperienceList().isEmpty()) {
            output.setExperienceList(input.getExperienceList().stream().map(el -> this.experienceRMapper.map(el)).collect(Collectors.toList()));
        }

        return output;

    }

}
