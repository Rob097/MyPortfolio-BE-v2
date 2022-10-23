package com.myprojects.myportfolio.core.experience.mappers;

import com.myprojects.myportfolio.clients.experience.ExperienceR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.skill.mappers.ExperienceSkillRMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ExperienceRMapper implements Mapper<ExperienceR, Experience> {

    @Autowired
    private ExperienceSkillRMapper experienceSkillRMapper;

    @Override
    public ExperienceR map(Experience input) {
        return this.map(input, new ExperienceR());
    }

    @Override
    public ExperienceR map(Experience input, ExperienceR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new ExperienceR();
        }

        output.setId(input.getId());
        if(input.getUser()!=null) {
            output.setUserId(input.getUser().getId());
        }
        output.setTitle(input.getTitle());
        if(input.getEmploymentType()!=null) {
            output.setEmploymentType(input.getEmploymentType().name());
        }
        output.setCompanyName(input.getCompanyName());
        output.setLocation(input.getLocation());
        output.setStartDate(input.getStartDate().toInstant());
        if(input.getEndDate()!=null) {
            output.setEndDate(input.getEndDate().toInstant());
        }
        output.setDescription(input.getDescription());
        if(input.getSkills()!=null && !input.getSkills().isEmpty()) {
            output.setSkills(input.getSkills().stream().map(el -> this.experienceSkillRMapper.map(el)).collect(Collectors.toSet()));
        }

        return output;
    }

}
