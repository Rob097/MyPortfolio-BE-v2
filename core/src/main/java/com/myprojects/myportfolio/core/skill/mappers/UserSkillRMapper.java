package com.myprojects.myportfolio.core.skill.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.skill.UserSkillR;
import com.myprojects.myportfolio.core.skill.UserSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSkillRMapper implements Mapper<UserSkillR, UserSkill> {

    @Autowired
    private SkillRMapper skillRMapper;

    @Override
    public UserSkillR map(UserSkill input) { return this.map(input, new UserSkillR()); }

    @Override
    public UserSkillR map(UserSkill input, UserSkillR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new UserSkillR();
        }

        if(input.getUser()!=null) {
            output.setUserId(input.getUser().getId());
        }
        if(input.getSkill()!=null) {
            output.setSkill(skillRMapper.map(input.getSkill()));
        }

        return output;
    }
}
