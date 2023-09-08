package com.myprojects.myportfolio.core.skill.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.skill.SkillCategoryR;
import com.myprojects.myportfolio.clients.skill.SkillR;
import com.myprojects.myportfolio.core.skill.Skill;
import com.myprojects.myportfolio.core.skill.SkillsCategory;
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
        output.setCategory(this.mapCategory(input.getCategory()));

        return output;
    }

    public SkillCategoryR mapCategory(SkillsCategory input) {
        if(input==null){
            return null;
        }

        SkillCategoryR output = new SkillCategoryR();
        output.setId(input.getId());
        output.setName(input.getName());

        return output;
    }
}
