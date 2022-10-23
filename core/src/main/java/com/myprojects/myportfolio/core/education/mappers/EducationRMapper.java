package com.myprojects.myportfolio.core.education.mappers;

import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.core.education.Education;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EducationRMapper implements Mapper<EducationR, Education> {
    @Override
    public EducationR map(Education input) {
        return this.map(input, new EducationR());
    }

    @Override
    public EducationR map(Education input, EducationR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new EducationR();
        }

        output.setId(input.getId());
        if(input.getUser()!=null) {
            output.setUserId(input.getUser().getId());
        }
        output.setSchool(input.getSchool());
        output.setDegree(input.getDegree());
        output.setField(input.getField());
        output.setStartDate(input.getStartDate().toInstant());
        if(input.getEndDate()!=null) {
            output.setEndDate(input.getEndDate().toInstant());
        }
        output.setGrade(input.getGrade());
        output.setDescription(input.getDescription());
        if(input.getStories()!=null && !input.getStories().isEmpty()){
            output.setStoriesId(input.getStories().stream().map(el -> el.getId()).collect(Collectors.toSet()));
        }

        return output;
    }
}
