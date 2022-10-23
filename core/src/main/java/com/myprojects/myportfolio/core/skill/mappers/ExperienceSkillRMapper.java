package com.myprojects.myportfolio.core.skill.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.skill.ExperienceSkillR;
import com.myprojects.myportfolio.core.skill.ExperienceSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExperienceSkillRMapper implements Mapper<ExperienceSkillR, ExperienceSkill> {

    @Autowired
    private SkillRMapper skillRMapper;

    @Override
    public ExperienceSkillR map(ExperienceSkill input) { return this.map(input, new ExperienceSkillR()); }

    @Override
    public ExperienceSkillR map(ExperienceSkill input, ExperienceSkillR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new ExperienceSkillR();
        }

        if(input.getExperience()!=null) {
            output.setExperienceId(input.getExperience().getId());
        }
        if(input.getSkill()!=null) {
            output.setSkill(skillRMapper.map(input.getSkill()));
        }

        return output;
    }
}
