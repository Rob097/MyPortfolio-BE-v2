package com.myprojects.myportfolio.core.services.skills;

import com.myprojects.myportfolio.core.dao.skills.NewSkill;
import com.myprojects.myportfolio.core.repositories.skills.SkillRepository;
import com.myprojects.myportfolio.core.services.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service(value = "newSkillService")
@Transactional
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class SkillService extends BaseService<NewSkill> implements SkillServiceI {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        super();
        this.repository = skillRepository;

        this.skillRepository = skillRepository;
    }

}
