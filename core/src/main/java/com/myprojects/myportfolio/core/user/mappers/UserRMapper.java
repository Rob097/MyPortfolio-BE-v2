package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.diary.mappers.SyntheticDiaryRMapper;
import com.myprojects.myportfolio.core.education.mappers.SyntheticEducationRMapper;
import com.myprojects.myportfolio.core.experience.mappers.SyntheticExperienceRMapper;
import com.myprojects.myportfolio.core.project.mappers.SyntheticProjectRMapper;
import com.myprojects.myportfolio.core.skill.mappers.SkillRMapper;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserRMapper implements Mapper<UserR, User> {

    @Autowired
    private SyntheticUserRMapper syntheticMapper;

    @Autowired
    private SyntheticDiaryRMapper diaryRMapper;

    @Autowired
    private SyntheticProjectRMapper projectRMapper;

    @Autowired
    private SkillRMapper skillRMapper;

    @Autowired
    private SyntheticEducationRMapper educationRMapper;

    @Autowired
    private SyntheticExperienceRMapper experienceRMapper;

    @Override
    public UserR map(User input){
        return this.map(input, new UserR());
    }

    @Override
    public UserR map(User input, UserR output){

        output = this.syntheticMapper.map(input, output);

        if(output==null){
            return null;
        }

        if (input.getDiary() != null) {
            output.setDiary(this.diaryRMapper.map(input.getDiary()));
        }
        if (input.getProjects() != null && !input.getProjects().isEmpty()) {
            output.setProjects(input.getProjects().stream().map(el -> this.projectRMapper.map(el)).collect(Collectors.toList()));
        }
        if (input.getSkills() != null && !input.getSkills().isEmpty()) {
            output.setSkills(input.getSkills().stream().map(el -> this.skillRMapper.map(el)).collect(Collectors.toList()));
        }
        if (input.getEducations() != null && !input.getEducations().isEmpty()) {
            output.setEducationList(input.getEducations().stream().map(el -> this.educationRMapper.map(el)).collect(Collectors.toList()));
        }
        if (input.getExperiences() != null && !input.getExperiences().isEmpty()) {
            output.setExperienceList(input.getExperiences().stream().map(el -> this.experienceRMapper.map(el)).collect(Collectors.toList()));
        }

        return output;

    }

}
