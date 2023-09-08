package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SyntheticUserRMapper implements Mapper<UserR, User> {

    @Autowired
    private AddressRMapper addressRMapper;

    @Override
    public UserR map(User input, UserR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new UserR();
        }

        output.setId(input.getId());
        if(input.getSlug()!=null) {
            output.setSlug(input.getSlug());
        }
        output.setFirstName(input.getFirstName());
        output.setLastName(input.getLastName());
        output.setEmail(input.getEmail());
        output.setPhone(input.getPhone());
        output.setAge(input.getAge());
        output.setNationality(input.getNationality());
        output.setTitle(input.getTitle());
        output.setDescription(input.getDescription());
        output.setAddress(addressRMapper.map(input));
        if(input.getSex()!=null) {
            output.setSex(input.getSex().toString());
        }

        return output;
    }
}
