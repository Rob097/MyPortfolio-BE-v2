package com.myprojects.myportfolio.core.skill;

import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.skill.SkillCategoryR;
import com.myprojects.myportfolio.core.skill.mappers.SkillMapper;
import com.myprojects.myportfolio.core.skill.mappers.SkillRMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${core-module-basic-path}" + "/skillsCategory")
@Slf4j
public class SkillsCategoryController implements IController<SkillCategoryR> {

    private final SkillsCategoryService skillsCategoryService;

    private final SkillRMapper skillRMapper;

    private final SkillMapper skillMapper;

    private final HttpServletRequest httpServletRequest;

    public SkillsCategoryController(SkillsCategoryService skillsCategoryService, SkillRMapper skillRMapper, SkillMapper skillMapper, HttpServletRequest httpServletRequest) {
        this.skillsCategoryService = skillsCategoryService;
        this.skillRMapper = skillRMapper;
        this.skillMapper = skillMapper;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResources<SkillCategoryR>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view,
            Pageable pageable
    ) throws Exception {
        Specification<SkillsCategory> specifications = this.defineFiltersAndStoreView(filters, view, this.httpServletRequest);

        Slice<SkillsCategory> skills = skillsCategoryService.findAll(specifications, pageable);

        return this.buildSuccessResponses(skills.map(skillRMapper::mapCategory));
    }

    @Override
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<SkillCategoryR>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(id, "Mandatory parameter is missing: id.");
        this.storeRequestView(view, httpServletRequest);

        SkillsCategory skillsCategory = this.skillsCategoryService.findById(id);

        return this.buildSuccessResponse(skillRMapper.mapCategory(skillsCategory));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageResource<SkillCategoryR>> create(@RequestBody SkillCategoryR skillsCategory) throws Exception {
        Validate.notNull(skillsCategory, "No valid resource was provided..");

        SkillsCategory newSkill = this.skillsCategoryService.save(this.skillMapper.mapCategory(skillsCategory));
        return this.buildSuccessResponse(skillRMapper.mapCategory(newSkill));
    }

    @Override
    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageResource<SkillCategoryR>> update(Integer id, SkillCategoryR resource) throws Exception {
        throw new UnsupportedOperationException("Method not supported.");
    }

    @Override
    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole(T(ApplicationUserRole).ADMIN.getName())")
    public ResponseEntity<MessageResource<SkillCategoryR>> delete(@PathVariable("id") Integer id) throws Exception {
        Validate.notNull(id, "No valid parameters were provided.");

        SkillsCategory skillToDelete = this.skillsCategoryService.findById(id);
        Validate.notNull(skillToDelete, "No valid skillsCategory found with id " + id);

        this.skillsCategoryService.delete(skillToDelete);
        return this.buildSuccessResponse(skillRMapper.mapCategory(skillToDelete));
    }
    
}
