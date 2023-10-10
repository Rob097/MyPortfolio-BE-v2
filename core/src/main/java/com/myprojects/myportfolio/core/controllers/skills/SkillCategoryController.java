package com.myprojects.myportfolio.core.controllers.skills;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.core.controllers.BaseController;
import com.myprojects.myportfolio.core.dto.groups.OnCreate;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.mappers.skills.SkillCategoryMapper;
import com.myprojects.myportfolio.core.dao.skills.NewSkillCategory;
import com.myprojects.myportfolio.core.dto.skills.NewSkillCategoryDto;
import com.myprojects.myportfolio.core.services.skills.SkillCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    // SKILL CATEGORY CAN BE CREATED BY ANYONE BUT UPDATED AND DELETED ONLY BY SYS_ADMIN

    @Override
    @PostMapping()
    public ResponseEntity<MessageResource<NewSkillCategoryDto>> create(
            @Validated(OnCreate.class) @RequestBody NewSkillCategoryDto entity
    ) throws Exception {
        Validate.notNull(entity, resourceMissing());

        NewSkillCategory newEntity = service.save(mapper.mapToDao(entity));
        return this.buildSuccessResponse(mapper.mapToDto(newEntity));
    }

    @Override
    @PutMapping(value = "/{id}")
    // TODO reactivate PreAuthorize @PreAuthorize("hasAnyRole(T(ApplicationUserRole).SYS_ADMIN.getName())")
    public ResponseEntity<MessageResource<NewSkillCategoryDto>> update(
            @PathVariable("id") Integer id,
            @Validated(OnUpdate.class) @RequestBody NewSkillCategoryDto entity
    ) throws Exception {
        Validate.notNull(entity, resourceMissing());
        Validate.notNull(entity.getId(), fieldMissing("id"));
        Validate.isTrue(entity.getId().equals(id), "The request's id and the body's id are different.");

        NewSkillCategory updatedEntity = service.update(mapper.mapToDao(entity));
        return this.buildSuccessResponse(mapper.mapToDto(updatedEntity));
    }

    @Override
    @DeleteMapping(value = "/{id}")
    // TODO reactivate PreAuthorize @PreAuthorize("hasAnyRole(T(ApplicationUserRole).SYS_ADMIN.getName())")
    public ResponseEntity<MessageResource<NewSkillCategoryDto>> delete(
            @PathVariable("id") Integer id
    ) throws Exception {
        Validate.notNull(id, fieldMissing("id"));

        NewSkillCategory entityToDelete = service.findById(id);
        Validate.notNull(entityToDelete, noEntityFound(id));

        service.delete(entityToDelete);
        return this.buildSuccessResponse(mapper.mapToDto(entityToDelete));
    }

}
