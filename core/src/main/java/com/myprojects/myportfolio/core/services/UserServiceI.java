package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.NewUser;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserServiceI extends BaseServiceI<NewUser> {

    NewUser findBy(Specification<NewUser> specification);

    List<String> findAllSlugs();

}
