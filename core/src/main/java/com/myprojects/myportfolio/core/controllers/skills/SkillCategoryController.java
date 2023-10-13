package com.myprojects.myportfolio.core.controllers.skills;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.core.controllers.BaseController;
import com.myprojects.myportfolio.core.dto.groups.OnCreate;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.mappers.skills.SkillCategoryMapper;
import com.myprojects.myportfolio.core.dao.skills.SkillCategory;
import com.myprojects.myportfolio.core.dto.skills.SkillCategoryDto;
import com.myprojects.myportfolio.core.services.skills.SkillCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("SkillCategoryController")
@RequestMapping("${core-module-basic-path}" + "/skillsCategories")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class SkillCategoryController extends BaseController<SkillCategory, SkillCategoryDto> {

    private final SkillCategoryService skillCategoryService;

    private final SkillCategoryMapper skillCategoryMapper;

    public SkillCategoryController(SkillCategoryService skillCategoryService, SkillCategoryMapper skillCategoryMapper) {
        this.service = skillCategoryService;
        this.mapper = skillCategoryMapper;

        this.skillCategoryService = skillCategoryService;
        this.skillCategoryMapper = skillCategoryMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

    // SKILL CATEGORY CAN BE CREATED BY ANYONE BUT UPDATED AND DELETED ONLY BY SYS_ADMIN

    @Override
    @PostMapping()
    public ResponseEntity<MessageResource<SkillCategoryDto>> create(
            @Validated(OnCreate.class) @RequestBody SkillCategoryDto entity
    ) throws Exception {
        Validate.notNull(entity, resourceMissing());

        SkillCategory newEntity = service.save(mapper.mapToDao(entity));
        return this.buildSuccessResponse(mapper.mapToDto(newEntity));
    }

    @Override
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.getName())")
    public ResponseEntity<MessageResource<SkillCategoryDto>> update(
            @PathVariable("id") Integer id,
            @Validated(OnUpdate.class) @RequestBody SkillCategoryDto entity
    ) throws Exception {
        Validate.notNull(entity, resourceMissing());
        Validate.notNull(entity.getId(), fieldMissing("id"));
        Validate.isTrue(entity.getId().equals(id), "The request's id and the body's id are different.");

        SkillCategory updatedEntity = service.update(mapper.mapToDao(entity));
        return this.buildSuccessResponse(mapper.mapToDto(updatedEntity));
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.getName())")
    public ResponseEntity<MessageResource<SkillCategoryDto>> delete(
            @PathVariable("id") Integer id
    ) throws Exception {
        Validate.notNull(id, fieldMissing("id"));

        SkillCategory entityToDelete = service.findById(id);
        Validate.notNull(entityToDelete, noEntityFound(id));

        service.delete(entityToDelete);
        return this.buildSuccessResponse(mapper.mapToDto(entityToDelete));
    }

}
