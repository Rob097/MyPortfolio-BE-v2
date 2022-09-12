package com.myprojects.myportfolio.core.project;

import com.myprojects.myportfolio.clients.project.ProjectR;
import com.myprojects.myportfolio.core.user.mappers.UserRMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectRMapper {

    @Autowired
    private UserRMapper userRMapper;

    public ProjectR map(Project input){
        return this.map(input, new ProjectR());
    }

    public ProjectR map(Project input, ProjectR output){
        if(input==null){
            return null;
        }
        if(output==null){
            output = new ProjectR();
        }

        output.setId(input.getId());
        output.setName(input.getName());
        output.setOwner(userRMapper.map(input.getOwner()));

        return output;

    }
    
}
