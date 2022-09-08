package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.clients.user.UserR;
import org.springframework.stereotype.Component;

@Component
public class UserRMapper {

    public UserR map(User input){
        return this.map(input, new UserR());
    }

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
