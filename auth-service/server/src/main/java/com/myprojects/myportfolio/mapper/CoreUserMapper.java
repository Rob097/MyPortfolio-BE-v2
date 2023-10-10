package com.myprojects.myportfolio.mapper;

import com.myprojects.myportfolio.dao.DBUser;
import com.myprojects.myportfolio.dto.CoreUser;
import org.springframework.stereotype.Component;

@Component
public class CoreUserMapper implements Mapper<CoreUser, DBUser> {
    @Override
    public CoreUser map(DBUser input) {
        return map(input, new CoreUser());
    }

    @Override
    public CoreUser map(DBUser input, CoreUser output) {
        if (input == null) {
            return null;
        }
        if (output == null) {
            output = new CoreUser();
        }

        output.setId(input.getId());
        output.setFirstName(input.getFirstName());
        output.setLastName(input.getLastName());
        output.setEmail(input.getEmail());

        return output;
    }
}
