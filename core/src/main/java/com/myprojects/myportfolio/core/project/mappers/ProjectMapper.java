package com.myprojects.myportfolio.core.project.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.project.ProjectR;
import com.myprojects.myportfolio.core.project.Project;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper implements Mapper<Project, ProjectR> {

    @Override
    public Project map(ProjectR input) { return this.map(input, new Project()); }

    @Override
    public Project map(ProjectR input, Project output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new Project();
        }

        output.setId(input.getId());
        output.setTitle(input.getTitle());
        output.setDescription(input.getDescription());
        if(input.getEntryDateTime()!=null) {
            output.setEntryDateTime(input.getEntryDateTime());
        }
        if(input.getUser()!=null) {
            output.setUser(User.builder().id(input.getUser().getId()).build());
        }

        return output;
    }
}
