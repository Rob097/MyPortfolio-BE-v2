package com.myprojects.myportfolio.core.newDataModel.controllers.skills;

import com.myprojects.myportfolio.core.newDataModel.controllers.BaseController;
import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewSkillCategory;
import com.myprojects.myportfolio.core.newDataModel.dto.skills.NewSkillCategoryDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.skills.SkillCategoryMapper;
import com.myprojects.myportfolio.core.newDataModel.services.skills.SkillCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("newSkillCategoryController")
@RequestMapping("${core-module-basic-path}" + "/new/skillsCategories")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class SkillCategoryController extends BaseController<NewSkillCategory, NewSkillCategoryDto> {

    private final SkillCategoryService skillCategoryService;

    private final SkillCategoryMapper skillCategoryMapper;

    public SkillCategoryController(SkillCategoryService skillCategoryService, SkillCategoryMapper skillCategoryMapper) {
        this.service = skillCategoryService;
        this.mapper = skillCategoryMapper;

        this.skillCategoryService = skillCategoryService;
        this.skillCategoryMapper = skillCategoryMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

}
