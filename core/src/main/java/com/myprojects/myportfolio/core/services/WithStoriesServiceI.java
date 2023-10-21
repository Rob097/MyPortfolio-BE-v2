package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.BaseDao;

public interface WithStoriesServiceI<T extends BaseDao> extends BaseServiceI<T> {
    void removeStoriesFromEntity(Integer entityId, Integer[] storyIds);
}
