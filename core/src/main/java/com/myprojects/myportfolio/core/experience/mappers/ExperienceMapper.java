package com.myprojects.myportfolio.core.experience.mappers;

import com.myprojects.myportfolio.clients.experience.ExperienceR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.core.experience.EmploymentTypeEnum;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

@Component
public class ExperienceMapper implements Mapper<Experience, ExperienceR> {
    @Override
    public Experience map(ExperienceR input) {
        return this.map(input, new Experience());
    }

    @Override
    public Experience map(ExperienceR input, Experience output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new Experience();
        }

        output.setId(input.getId());
        if(input.getUserId()!=null) {
            output.setUser(User.builder().id(input.getUserId()).build());
        }
        output.setTitle(input.getTitle());
        if(input.getEmploymentType()!=null) {
            output.setEmploymentType(EmploymentTypeEnum.valueOf(input.getEmploymentType()));
        }
        output.setCompanyName(input.getCompanyName());
        output.setLocation(input.getLocation());
        if(input.getStartDate()!=null) {
            ZonedDateTime zdt = ZonedDateTime.ofInstant(input.getStartDate(), ZoneId.systemDefault());
            output.setStartDate(GregorianCalendar.from(zdt));
        }
        if(input.getEndDate()!=null) {
            ZonedDateTime zdt = ZonedDateTime.ofInstant(input.getEndDate(), ZoneId.systemDefault());
            output.setStartDate(GregorianCalendar.from(zdt));
        }
        output.setDescription(input.getDescription());

        return output;
    }
}
