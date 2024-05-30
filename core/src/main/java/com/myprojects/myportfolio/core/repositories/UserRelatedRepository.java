package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.BaseDao;

import java.util.List;
import java.util.Optional;


public interface UserRelatedRepository<A extends BaseDao> {

    Optional<List<String>> findSlugsByUserId(Integer userId, boolean isCurrentUser);

}
