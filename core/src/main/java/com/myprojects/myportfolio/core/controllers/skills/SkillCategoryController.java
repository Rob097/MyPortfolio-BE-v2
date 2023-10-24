package com.myprojects.myportfolio.core.controllers.skills;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.core.controllers.BaseController;
import com.myprojects.myportfolio.core.dao.skills.SkillCategory;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.dto.skills.SkillCategoryDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillCategoryMapper;
import com.myprojects.myportfolio.core.services.skills.SkillCategoryService;
import lombok.extern.slf4j.Slf4j;
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

    public final boolean isPostAuthorized = true;
    public final boolean isPutAuthorized = true;

    public SkillCategoryController(SkillCategoryService skillCategoryService, SkillCategoryMapper skillCategoryMapper) {
        super(skillCategoryService, skillCategoryMapper);

        this.skillCategoryService = skillCategoryService;
        this.skillCategoryMapper = skillCategoryMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

    // SKILL CATEGORY CAN BE CREATED BY ANYONE BUT UPDATED AND DELETED ONLY BY SYS_ADMIN
    @Override
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.name())")
    public ResponseEntity<MessageResource<SkillCategoryDto>> update(
            @PathVariable("id") Integer id,
            @Validated(OnUpdate.class) @RequestBody SkillCategoryDto entity
    ) throws Exception {
        return super.update(id, entity);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.getName())")
    public ResponseEntity<MessageResource<SkillCategoryDto>> delete(
            @PathVariable("id") Integer id
    ) throws Exception {
        return super.delete(id);
    }

}
