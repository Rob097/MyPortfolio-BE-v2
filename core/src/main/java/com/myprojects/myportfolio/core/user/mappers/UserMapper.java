package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.diary.mappers.DiaryMapper;
import com.myprojects.myportfolio.core.education.mappers.EducationMapper;
import com.myprojects.myportfolio.core.experience.mappers.ExperienceMapper;
import com.myprojects.myportfolio.core.project.mappers.ProjectMapper;
import com.myprojects.myportfolio.core.skill.mappers.SkillMapper;
import com.myprojects.myportfolio.core.user.User;
import com.myprojects.myportfolio.core.user.UserSkill;
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
        output.setPhone(input.getPhone());
        output.setAge(input.getAge());
        output.setNationality(input.getNationality());
        output.setTitle(input.getTitle());
        output.setDescription(input.getDescription());
        if(input.getAddress()!=null) {
            output.setNation(input.getAddress().getNation());
            output.setProvince(input.getAddress().getProvince());
            output.setCity(input.getAddress().getCity());
            output.setCap(input.getAddress().getCap());
            output.setAddress(input.getAddress().getAddress());
        }
        if(input.getSlug()!=null) {
            output.setSlug(input.getSlug());
        }
        if(!Strings.isNullOrEmpty(input.getSex())) {
            output.setSex(input.getSex().equals("MALE") ? User.Sex.MALE : input.getSex().equals("FEMALE") ? User.Sex.FEMALE : null);
        }
        if (input.getDiaries() != null && !input.getDiaries().isEmpty()) {
            output.setDiaries(input.getDiaries().stream().map(el -> this.diaryMapper.map(el)).collect(Collectors.toList()));
        }
        if(input.getProjects()!=null && !input.getProjects().isEmpty()) {
            output.setProjects(input.getProjects().stream().map(el -> this.projectMapper.map(el)).collect(Collectors.toList()));
        }
        if(input.getSkills()!=null && !input.getSkills().isEmpty()) {
            output.setSkills(input.getSkills().stream().map(this::userSkillMap).collect(Collectors.toSet()));
        }
        if(input.getEducations()!=null && !input.getEducations().isEmpty()) {
            output.setEducations(input.getEducations().stream().map(el -> this.educationMapper.map(el)).collect(Collectors.toList()));
        }
        if(input.getExperiences()!=null && !input.getExperiences().isEmpty()) {
            output.setExperiences(input.getExperiences().stream().map(el -> this.experienceMapper.map(el)).collect(Collectors.toList()));
        }

        return output;

    }

    private UserSkill userSkillMap(UserR.UserSkillR userSkillR) {
        UserSkill userSkill = new UserSkill();

        userSkill.setUser(User.builder().id(userSkillR.getUserId()).build());
        userSkill.setSkill(skillMapper.map(userSkillR.getSkill()));
        userSkill.setMain(userSkillR.getIsMain());
        userSkill.setOrderId(userSkillR.getOrderId());

        return userSkill;
    }

}
