package com.myprojects.myportfolio.core.configAndUtils;

import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import com.myprojects.myportfolio.core.newDataModel.dto.BaseDto;

public interface UtilsServiceI {

    String toSlug(String input);

    boolean hasId(Integer id);

    NewUser getCurrentLoggedInUser();

    <T extends BaseDto> boolean isOfCurrentUser(T entity, boolean isCreate);

}
