package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import org.springframework.data.jpa.domain.Specification;

public interface UserServiceI extends BaseServiceI<NewUser> {

    NewUser findBy(Specification<NewUser> specification);

}
