package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserR> {

    @Override
    public User map(UserR input){
        return this.map(input, new User());
    }

    @Override
    public User map(UserR input, User output) {
        if (input == null) {
            return null;
        }
        if (output == null) {
            output = new User();
        }

        output.setId(input.getId());
        output.setFirstName(input.getFirstName());
        output.setLastName(input.getLastName());
        output.setEmail(input.getEmail());

        return output;

    }

}
