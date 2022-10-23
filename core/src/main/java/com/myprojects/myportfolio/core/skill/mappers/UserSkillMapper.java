package com.myprojects.myportfolio.core.skill.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.skill.UserSkillR;
import com.myprojects.myportfolio.core.skill.UserSkill;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSkillMapper implements Mapper<UserSkill, UserSkillR> {

    @Autowired
    private SkillMapper skillMapper;

    @Override
    public UserSkill map(UserSkillR input) { return this.map(input, new UserSkill()); }

    @Override
    public UserSkill map(UserSkillR input, UserSkill output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new UserSkill();
        }

        if(input.getUserId()!=null) {
            output.setUser(User.builder().id(input.getUserId()).build());
        }
        if(input.getSkill()!=null) {
            output.setSkill(skillMapper.map(input.getSkill()));
        }

        return output;
    }
}
