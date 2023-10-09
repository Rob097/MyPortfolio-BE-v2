package com.myprojects.myportfolio.mapper;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.dao.DBUser;
import org.springframework.stereotype.Component;

@Component
public class DBUserMapper implements Mapper<UserR, DBUser> {
    @Override
    public UserR map(DBUser input) {
        return this.map(input, new UserR());
    }

    @Override
    public UserR map(DBUser input, UserR output) {
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
