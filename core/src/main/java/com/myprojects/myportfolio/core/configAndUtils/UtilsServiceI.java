package com.myprojects.myportfolio.core.configAndUtils;

import com.myprojects.myportfolio.core.newDataModel.dto.BaseDto;

public interface UtilsServiceI {

    String toSlug(String input);

    <T extends BaseDto> boolean isOfCurrentUser(T entity, boolean isCreate);

}
