package com.myprojects.myportfolio.core.controllers.skills;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.core.controllers.BaseController;
import com.myprojects.myportfolio.core.dao.skills.Skill;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.dto.skills.SkillDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillMapper;
import com.myprojects.myportfolio.core.services.skills.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("SkillController")
@RequestMapping("${core-module-basic-path}" + "/skills")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class SkillController extends BaseController<Skill, SkillDto> {

    private final SkillService skillService;

    private final SkillMapper skillMapper;

    public SkillController(SkillService skillService, SkillMapper skillMapper) {
        super(skillService, skillMapper);

        this.skillService = skillService;
        this.skillMapper = skillMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

    // SKILL CAN BE CREATED BY ANYONE BUT UPDATED AND DELETED ONLY BY SYS_ADMIN
    @Override
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.name())")
    public ResponseEntity<MessageResource<SkillDto>> update(
            @PathVariable("id") Integer id,
            @Validated(OnUpdate.class) @RequestBody SkillDto entity
    ) throws Exception {
        return super.update(id, entity);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.getName())")
    public ResponseEntity<MessageResource<SkillDto>> delete(
            @PathVariable("id") Integer id
    ) throws Exception {
        return super.delete(id);
    }

}
