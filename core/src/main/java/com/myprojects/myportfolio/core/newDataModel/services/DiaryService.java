package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;
import com.myprojects.myportfolio.core.newDataModel.repositories.DiaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@Service(value = "newDiaryService")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class DiaryService extends BaseService<NewDiary> implements DiaryServiceI {

    private final DiaryRepository diaryRepository;

    private final UserServiceI userService;

    public DiaryService(DiaryRepository diaryRepository, UserServiceI userService) {
        super();
        this.repository = diaryRepository;

        this.diaryRepository = diaryRepository;
        this.userService = userService;
    }

    @Override
    public NewDiary save(NewDiary diary) {
        if (diary.getUser() == null || diary.getUser().getId() == null) {
            diary.setUser(userService.getCurrentLoggedInUser());
        }

        return super.save(diary);
    }
}
