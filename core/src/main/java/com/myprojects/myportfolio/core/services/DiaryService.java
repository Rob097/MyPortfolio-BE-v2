package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Diary;
import com.myprojects.myportfolio.core.repositories.DiaryRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service(value = "DiaryService")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class DiaryService extends WithStoriesService<Diary> implements DiaryServiceI {

    private final DiaryRepository diaryRepository;

    private final StoryRepository storyRepository;

    public DiaryService(DiaryRepository diaryRepository, StoryRepository storyRepository) {
        super(diaryRepository, storyRepository);

        this.diaryRepository = diaryRepository;
        this.storyRepository = storyRepository;
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
