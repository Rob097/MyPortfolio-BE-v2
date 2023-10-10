package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserServiceI extends BaseServiceI<User> {

    User findBy(Specification<User> specification);

    List<String> findAllSlugs();

}
