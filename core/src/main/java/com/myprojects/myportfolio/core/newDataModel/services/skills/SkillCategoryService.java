package com.myprojects.myportfolio.core.newDataModel.services.skills;

import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewSkillCategory;
import com.myprojects.myportfolio.core.newDataModel.repositories.skills.SkillCategoryRepository;
import com.myprojects.myportfolio.core.newDataModel.services.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service(value = "newSkillCategoryService")
@Transactional
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class SkillCategoryService extends BaseService<NewSkillCategory> implements SkillCategoryServiceI {

    private final SkillCategoryRepository skillCategoryRepository;

    public SkillCategoryService(SkillCategoryRepository skillCategoryRepository) {
        super();
        this.repository = skillCategoryRepository;

        this.skillCategoryRepository = skillCategoryRepository;
    }

}
