package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.User;

import java.util.List;

public interface UserServiceI extends BaseServiceI<User> {

    List<String> findAllSlugs();

}
