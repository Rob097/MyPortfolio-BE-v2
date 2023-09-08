package com.myprojects.myportfolio.core.education.mappers;

import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.core.education.Education;
import com.myprojects.myportfolio.core.story.Story;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EducationMapper implements Mapper<Education, EducationR> {
    @Override
    public Education map(EducationR input) {
        return this.map(input, new Education());
    }

    @Override
    public Education map(EducationR input, Education output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new Education();
        }

        output.setId(input.getId());
        if(input.getSlug()!=null) {
            output.setSlug(input.getSlug());
        }
        if(input.getUser()!=null) {
            output.setUser(User.builder().id(input.getUser().getId()).build());
        }
        output.setSchool(input.getSchool());
        output.setDegree(input.getDegree());
        output.setField(input.getField());
        if(input.getStartDate()!=null) {
            output.setStartDate(input.getStartDate());
        }
        if(input.getEndDate()!=null) {
            output.setEndDate(input.getEndDate());
        }
        output.setGrade(input.getGrade());
        output.setDescription(input.getDescription());
        if(input.getStories()!=null && !input.getStories().isEmpty()){
            output.setStories(input.getStories().stream().map(el -> Story.builder().id(el.getId()).build()).collect(Collectors.toSet()));
        }

        return output;
    }
}
