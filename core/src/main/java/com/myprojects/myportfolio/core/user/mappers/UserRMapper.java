package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.project.mappers.ProjectRMapper;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserRMapper implements Mapper<UserR, User> {

    @Autowired
    private ProjectRMapper projectRMapper;

    @Override
    public UserR map(User input){
        return this.map(input, new UserR());
    }

    @Override
    public UserR map(User input, UserR output){
        if(input==null){
            return null;
        }
        if(output==null){
            output = new UserR();
        }

        output.setId(input.getId());
        output.setFirstName(input.getFirstName());
        output.setLastName(input.getLastName());
        output.setEmail(input.getEmail());
        if(input.getProjects()!=null && !input.getProjects().isEmpty()) {
            output.setProjects(input.getProjects().stream().map(el -> this.projectRMapper.map(el)).collect(Collectors.toList()));
        }

        return output;

    }

}
