package com.myprojects.myportfolio.core.skill.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.skill.SkillR;
import com.myprojects.myportfolio.core.skill.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillRMapper implements Mapper<SkillR, Skill> {
    @Override
    public SkillR map(Skill input) { return this.map(input, new SkillR()); }

    @Override
    public SkillR map(Skill input, SkillR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new SkillR();
        }

        output.setId(input.getId());
        output.setName(input.getName());

        return output;
    }
}
