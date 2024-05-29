package com.myprojects.myportfolio.core.configAndUtils;

import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.dto.BaseDto;

public interface UtilsServiceI {

    boolean hasId(Integer id);

    User getCurrentLoggedInUser();

    <T extends BaseDto> boolean isOfCurrentUser(T entity, boolean isCreate);

    <T extends BaseDao> boolean isOfCurrentUser(T entity, boolean isCreate);
}
