package com.myprojects.myportfolio.core.services.skills;

import com.myprojects.myportfolio.core.dao.skills.Skill;
import com.myprojects.myportfolio.core.repositories.skills.SkillRepository;
import com.myprojects.myportfolio.core.services.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service(value = "SkillService")
@Transactional
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class SkillService extends BaseService<Skill> implements SkillServiceI {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        super();
        this.repository = skillRepository;

        this.skillRepository = skillRepository;
    }

}
