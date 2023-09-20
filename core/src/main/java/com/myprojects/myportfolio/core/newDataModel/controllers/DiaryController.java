package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;
import com.myprojects.myportfolio.core.newDataModel.dto.NewDiaryDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.DiaryMapper;
import com.myprojects.myportfolio.core.newDataModel.services.DiaryServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("newDiaryController")
@RequestMapping("${core-module-basic-path}" + "/new/diaries")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class DiaryController extends BaseController<NewDiary, NewDiaryDto> {

    private final DiaryServiceI diaryService;

    private final DiaryMapper diaryMapper;

    public DiaryController(DiaryServiceI diaryService, DiaryMapper diaryMapper) {
        this.service = diaryService;
        this.mapper = diaryMapper;

        this.diaryService = diaryService;
        this.diaryMapper = diaryMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

}
