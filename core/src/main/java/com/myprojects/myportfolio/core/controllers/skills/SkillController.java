package com.myprojects.myportfolio.core.controllers.skills;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.core.controllers.BaseController;
import com.myprojects.myportfolio.core.dto.groups.OnCreate;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.dto.skills.NewSkillDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillMapper;
import com.myprojects.myportfolio.core.dao.skills.NewSkill;
import com.myprojects.myportfolio.core.services.skills.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    // SKILL CAN BE CREATED BY ANYONE BUT UPDATED AND DELETED ONLY BY SYS_ADMIN

    @Override
    @PostMapping()
    public ResponseEntity<MessageResource<NewSkillDto>> create(
            @Validated(OnCreate.class) @RequestBody NewSkillDto entity
    ) throws Exception {
        Validate.notNull(entity, resourceMissing());

        NewSkill newEntity = service.save(mapper.mapToDao(entity));
        return this.buildSuccessResponse(mapper.mapToDto(newEntity));
    }

    @Override
    @PutMapping(value = "/{id}")
    // TODO reactivate PreAuthorize @PreAuthorize("hasAnyRole(T(ApplicationUserRole).SYS_ADMIN.getName())")
    public ResponseEntity<MessageResource<NewSkillDto>> update(
            @PathVariable("id") Integer id,
            @Validated(OnUpdate.class) @RequestBody NewSkillDto entity
    ) throws Exception {
        Validate.notNull(entity, resourceMissing());
        Validate.notNull(entity.getId(), fieldMissing("id"));
        Validate.isTrue(entity.getId().equals(id), "The request's id and the body's id are different.");

        NewSkill updatedEntity = service.update(mapper.mapToDao(entity));
        return this.buildSuccessResponse(mapper.mapToDto(updatedEntity));
    }

    @Override
    @DeleteMapping(value = "/{id}")
    // TODO reactivate PreAuthorize @PreAuthorize("hasAnyRole(T(ApplicationUserRole).SYS_ADMIN.getName())")
    public ResponseEntity<MessageResource<NewSkillDto>> delete(
            @PathVariable("id") Integer id
    ) throws Exception {
        Validate.notNull(id, fieldMissing("id"));

        NewSkill entityToDelete = service.findById(id);
        Validate.notNull(entityToDelete, noEntityFound(id));

        service.delete(entityToDelete);
        return this.buildSuccessResponse(mapper.mapToDto(entityToDelete));
    }

}
