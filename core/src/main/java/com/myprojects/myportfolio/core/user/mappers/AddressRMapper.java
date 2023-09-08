package com.myprojects.myportfolio.core.user.mappers;

import com.myprojects.myportfolio.clients.general.AddressR;
import com.myprojects.myportfolio.clients.general.Mapper;
import com.myprojects.myportfolio.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class AddressRMapper implements Mapper<AddressR, User> {

    @Override
    public AddressR map(User input, AddressR output) {
        if(input==null){
            return null;
        }
        if(output==null){
            output = new AddressR();
        }

        output.setNation(input.getNation());
        output.setProvince(input.getProvince());
        output.setCity(input.getCity());
        output.setCap(input.getCap());
        output.setAddress(input.getAddress());

        return output;
    }
}
