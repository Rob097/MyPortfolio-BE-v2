package com.myprojects.myportfolio.core.experience.mappers;

import com.myprojects.myportfolio.clients.experience.ExperienceR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.core.experience.EmploymentTypeEnum;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.skill.mappers.SkillMapper;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ExperienceMapper implements Mapper<Experience, ExperienceR> {

    @Autowired
    private SkillMapper skillMapper;

    @Override
    public Experience map(ExperienceR input) {
        return this.map(input, new Experience());
    }

    @Override
    public Experience map(ExperienceR input, Experience output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new Experience();
        }

        output.setId(input.getId());
        if(input.getUser()!=null) {
            output.setUser(User.builder().id(input.getUser().getId()).build());
        }
        output.setTitle(input.getTitle());
        if(input.getEmploymentType()!=null) {
            output.setEmploymentType(EmploymentTypeEnum.valueOf(input.getEmploymentType()));
        }
        output.setCompanyName(input.getCompanyName());
        output.setLocation(input.getLocation());
        if(input.getStartDate()!=null) {
            output.setStartDate(input.getStartDate());
        }
        if(input.getEndDate()!=null) {
            output.setEndDate(input.getEndDate());
        }
        output.setDescription(input.getDescription());
        if(input.getSkills()!=null && !input.getSkills().isEmpty()) {
            output.setSkills(input.getSkills().stream().map(el -> this.skillMapper.map(el)).collect(Collectors.toSet()));
        }

        return output;
    }

}
