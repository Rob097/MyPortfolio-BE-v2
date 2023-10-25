package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.BaseDao;

import java.util.List;

public interface UserRelatedServiceI<A extends BaseDao> {

    List<String> findSlugsByUserId(Integer userId);

}
