package com.myprojects.myportfolio.core.newDataModel.controllers.skills;

import com.myprojects.myportfolio.core.newDataModel.controllers.BaseController;
import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewSkill;
import com.myprojects.myportfolio.core.newDataModel.dto.skills.NewSkillDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.skills.SkillMapper;
import com.myprojects.myportfolio.core.newDataModel.services.skills.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("newSkillController")
@RequestMapping("${core-module-basic-path}" + "/new/skills")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class SkillController extends BaseController<NewSkill, NewSkillDto> {

    private final SkillService skillService;

    private final SkillMapper skillMapper;

    public SkillController(SkillService skillService, SkillMapper skillMapper) {
        this.service = skillService;
        this.mapper = skillMapper;

        this.skillService = skillService;
        this.skillMapper = skillMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

}
