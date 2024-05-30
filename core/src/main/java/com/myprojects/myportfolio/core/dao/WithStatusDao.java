package com.myprojects.myportfolio.core.dao;

import com.myprojects.myportfolio.core.dao.enums.EntitiesStatusEnum;

public interface WithStatusDao {

    EntitiesStatusEnum status = null;

    EntitiesStatusEnum getStatus();

}
