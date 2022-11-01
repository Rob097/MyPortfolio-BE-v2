package com.myprojects.myportfolio.core.education.mappers;

import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.core.education.Education;
import org.springframework.stereotype.Component;

@Component
public class SyntheticEducationRMapper implements Mapper<EducationR, Education>{

    @Override
    public EducationR map(Education input, EducationR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new EducationR();
        }

        output.setId(input.getId());
        output.setSchool(input.getSchool());
        output.setDegree(input.getDegree());
        output.setField(input.getField());
        output.setStartDate(input.getStartDate());
        if(input.getEndDate()!=null) {
            output.setEndDate(input.getEndDate());
        }
        output.setGrade(input.getGrade());
        output.setDescription(input.getDescription());

        return output;

    }

}
