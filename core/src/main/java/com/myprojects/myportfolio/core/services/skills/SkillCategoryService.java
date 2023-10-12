package com.myprojects.myportfolio.core.services.skills;

import com.myprojects.myportfolio.core.dao.skills.SkillCategory;
import com.myprojects.myportfolio.core.repositories.skills.SkillCategoryRepository;
import com.myprojects.myportfolio.core.services.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service(value = "SkillCategoryService")
@Transactional
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class SkillCategoryService extends BaseService<SkillCategory> implements SkillCategoryServiceI {

    private final SkillCategoryRepository skillCategoryRepository;

    public SkillCategoryService(SkillCategoryRepository skillCategoryRepository) {
        super();
        this.repository = skillCategoryRepository;

        this.skillCategoryRepository = skillCategoryRepository;
    }

}
