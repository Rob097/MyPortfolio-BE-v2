package com.myprojects.myportfolio.core.skill.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.skill.ExperienceSkillR;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.skill.ExperienceSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExperienceSkillMapper implements Mapper<ExperienceSkill, ExperienceSkillR> {

    @Autowired
    private SkillMapper skillMapper;

    @Override
    public ExperienceSkill map(ExperienceSkillR input) { return this.map(input, new ExperienceSkill()); }

    @Override
    public ExperienceSkill map(ExperienceSkillR input, ExperienceSkill output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new ExperienceSkill();
        }

        if(input.getExperienceId()!=null) {
            output.setExperience(Experience.builder().id(input.getExperienceId()).build());
        }
        if(input.getSkill()!=null) {
            output.setSkill(skillMapper.map(input.getSkill()));
        }

        return output;
    }
}
