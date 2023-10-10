package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Diary;

public interface DiaryServiceI extends BaseServiceI<Diary> {
    // Remove one or more stories from a diary
    void removeStoriesFromDiary(Integer diaryId, Integer[] storyIds);
}
