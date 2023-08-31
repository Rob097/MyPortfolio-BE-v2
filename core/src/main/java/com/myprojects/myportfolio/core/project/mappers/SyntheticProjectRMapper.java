package com.myprojects.myportfolio.core.project.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.project.ProjectR;
import com.myprojects.myportfolio.core.project.Project;
import org.springframework.stereotype.Component;

@Component
public class SyntheticProjectRMapper implements Mapper<ProjectR, Project> {
    @Override
    public ProjectR map(Project input, ProjectR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new ProjectR();
        }

        output.setId(input.getId());
        output.setName(input.getName());
        output.setDescription(input.getDescription());
        output.setEntryDateTime(input.getEntryDateTime());

        return output;
    }
}
