package com.myprojects.myportfolio.core.project.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.project.ProjectR;
import com.myprojects.myportfolio.core.project.Project;
import com.myprojects.myportfolio.core.user.mappers.SyntheticUserRMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectRMapper implements Mapper<ProjectR, Project> {

    @Autowired
    private SyntheticProjectRMapper syntheticMapper;

    @Autowired
    private SyntheticUserRMapper userRMapper;

    @Override
    public ProjectR map(Project input) {
        return this.map(input, new ProjectR());
    }

    @Override
    public ProjectR map(Project input, ProjectR output) {

        output = this.syntheticMapper.map(input, output);

        if(output==null){
            return null;
        }

        if (input.getUser() != null) {
            output.setUser(this.userRMapper.map(input.getUser()));
        }

        return output;
    }
}
