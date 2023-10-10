package com.myprojects.myportfolio.core.configAndUtils;

import com.myprojects.myportfolio.core.dao.NewUser;
import com.myprojects.myportfolio.core.dto.BaseDto;

public interface UtilsServiceI {

    boolean hasId(Integer id);

    NewUser getCurrentLoggedInUser();

    <T extends BaseDto> boolean isOfCurrentUser(T entity, boolean isCreate);

}
