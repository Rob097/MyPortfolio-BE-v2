package com.myprojects.myportfolio.core.experience.mappers;

import com.myprojects.myportfolio.clients.experience.ExperienceR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.skill.mappers.SkillRMapper;
import com.myprojects.myportfolio.core.user.mappers.SyntheticUserRMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ExperienceRMapper implements Mapper<ExperienceR, Experience> {

    @Autowired
    private SyntheticExperienceRMapper syntheticMapper;

    @Autowired
    private SyntheticUserRMapper userRMapper;

    @Autowired
    private SkillRMapper skillRMapper;

    @Override
    public ExperienceR map(Experience input) {
        return this.map(input, new ExperienceR());
    }

    @Override
    public ExperienceR map(Experience input, ExperienceR output) {

        output = this.syntheticMapper.map(input, output);

        if(output==null){
            return null;
        }

        if(input.getUser()!=null) {
            output.setUser(userRMapper.map(input.getUser()));
        }
        if(input.getSkills()!=null && !input.getSkills().isEmpty()) {
            output.setSkills(input.getSkills().stream().map(el -> this.skillRMapper.map(el)).collect(Collectors.toSet()));
        }

        return output;
    }

}
