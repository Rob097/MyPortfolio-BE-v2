package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class SyntheticUserRMapper implements Mapper<UserR, User> {
    @Override
    public UserR map(User input, UserR output) {
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
        output.setAge(input.getAge());
        output.setNationality(input.getNationality());
        output.setNation(input.getNation());
        output.setProvince(input.getProvince());
        output.setCity(input.getCity());
        output.setCap(input.getCap());
        output.setAddress(input.getAddress());
        if(input.getSex()!=null) {
            output.setSex(input.getSex().toString());
        }

        return output;
    }
}
