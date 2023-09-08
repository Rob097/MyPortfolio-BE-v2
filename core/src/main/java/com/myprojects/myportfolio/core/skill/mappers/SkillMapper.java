package com.myprojects.myportfolio.core.skill.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.skill.SkillCategoryR;
import com.myprojects.myportfolio.clients.skill.SkillR;
import com.myprojects.myportfolio.core.skill.Skill;
import com.myprojects.myportfolio.core.skill.SkillsCategory;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper implements Mapper<Skill, SkillR> {
    @Override
    public Skill map(SkillR input) { return this.map(input, new Skill()); }

    @Override
    public Skill map(SkillR input, Skill output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new Skill();
        }

        output.setId(input.getId());
        output.setName(input.getName());
        output.setCategory(this.mapCategory(input.getCategory()));

        return output;
    }

    public SkillsCategory mapCategory(SkillCategoryR input) {
        if(input==null){
            return null;
        }

        SkillsCategory output = new SkillsCategory();
        output.setId(input.getId());
        output.setName(input.getName());

        return output;
    }
}
