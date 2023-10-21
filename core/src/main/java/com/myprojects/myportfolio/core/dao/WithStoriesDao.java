package com.myprojects.myportfolio.core.dao;

import java.util.HashSet;
import java.util.Set;

public interface WithStoriesDao {

    Set<Story> stories = new HashSet<>();

    Integer mainStoryId = null;

    Set<Story> getStories();

    Integer getMainStoryId();

}
