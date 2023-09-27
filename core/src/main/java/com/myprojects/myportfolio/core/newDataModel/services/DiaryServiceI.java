package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;

public interface DiaryServiceI extends BaseServiceI<NewDiary> {
    // Remove one or more stories from a diary
    void removeStoriesFromDiary(Integer diaryId, Integer[] storyIds);
}
