package com.myprojects.myportfolio.core.education.mappers;

import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.core.education.Education;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

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
        if(input.getUserId()!=null) {
            output.setUser(User.builder().id(input.getUserId()).build());
        }
        output.setSchool(input.getSchool());
        output.setDegree(input.getDegree());
        output.setField(input.getField());
        if(input.getStartDate()!=null) {
            ZonedDateTime zdt = ZonedDateTime.ofInstant(input.getStartDate(), ZoneId.systemDefault());
            output.setStartDate(GregorianCalendar.from(zdt));
        }
        if(input.getEndDate()!=null) {
            ZonedDateTime zdt = ZonedDateTime.ofInstant(input.getEndDate(), ZoneId.systemDefault());
            output.setStartDate(GregorianCalendar.from(zdt));
        }
        output.setGrade(input.getGrade());
        output.setDescription(input.getDescription());

        return output;
    }
}
