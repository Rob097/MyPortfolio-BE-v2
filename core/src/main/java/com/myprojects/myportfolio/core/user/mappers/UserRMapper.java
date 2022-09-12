package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserRMapper implements mapper<UserR, User> {

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

        return output;

    }

}
