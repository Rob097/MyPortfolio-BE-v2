package com.myprojects.myportfolio.core.skill;

import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.skill.SkillR;
import com.myprojects.myportfolio.core.skill.mappers.SkillMapper;
import com.myprojects.myportfolio.core.skill.mappers.SkillRMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${core-module-basic-path}" + "/skills")
@Slf4j
public class SkillController implements IController<SkillR> {

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillRMapper skillRMapper;

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResources<SkillR>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view,
            Pageable pageable
    ) throws Exception {
        Specification<Skill> specifications = this.defineFiltersAndStoreView(filters, view, this.httpServletRequest);

        Slice<Skill> skills = skillService.findAll(specifications, pageable);

        return this.buildSuccessResponses(skills.map(skill -> skillRMapper.map(skill)));
    }

    @Override
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<SkillR>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(id, "Mandatory parameter is missing: id.");
        this.storeRequestView(view, httpServletRequest);

        Skill skill = this.skillService.findById(id);

        return this.buildSuccessResponse(skillRMapper.map(skill));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageResource<SkillR>> create(@RequestBody SkillR skill) throws Exception {
        Validate.notNull(skill, "No valid resource was provided..");

        Skill newSkill = this.skillService.save(this.skillMapper.map(skill));
        return this.buildSuccessResponse(skillRMapper.map(newSkill));
    }

    @Override
    @PutMapping
    public ResponseEntity<MessageResource<SkillR>> update(Integer id, SkillR resource) throws Exception {
        throw new UnsupportedOperationException("Method not supported.");
    }

    @Override
    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole(T(ApplicationUserRole).ADMIN.getName())")
    public ResponseEntity<MessageResource<SkillR>> delete(@PathVariable("id") Integer id) throws Exception {
        Validate.notNull(id, "No valid parameters were provided.");

        Skill skillToDelete = this.skillService.findById(id);
        Validate.notNull(skillToDelete, "No valid skill found with id " + id);

        this.skillService.delete(skillToDelete);
        return this.buildSuccessResponse(skillRMapper.map(skillToDelete));
    }
}
