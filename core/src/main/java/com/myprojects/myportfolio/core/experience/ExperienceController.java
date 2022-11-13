package com.myprojects.myportfolio.core.experience;

import com.myprojects.myportfolio.clients.experience.ExperienceR;
import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.experience.mappers.ExperienceMapper;
import com.myprojects.myportfolio.core.experience.mappers.ExperienceRMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("${core-module-basic-path}" + "/experiences")
public class ExperienceController implements IController<ExperienceR> {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private ExperienceRMapper experienceRMapper;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResources<ExperienceR>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view,
            Pageable pageable
    ) throws Exception {
        Specification<Experience> specifications = this.defineFiltersAndStoreView(filters, view, this.httpServletRequest);

        Slice<Experience> experiences = experienceService.findAll(specifications, pageable);

        return this.buildSuccessResponses(experiences.map(experience -> experienceRMapper.map(experience)));
    }

    @Override
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<ExperienceR>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(id, "Mandatory parameter is missing: id.");
        this.storeRequestView(view, httpServletRequest);

        Experience experience = experienceService.findById(id);

        return this.buildSuccessResponse(experienceRMapper.map(experience));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<ExperienceR>> create(@RequestBody ExperienceR experience) throws Exception {
        Validate.notNull(experience, "No valid resource was provided..");

        Experience newExperience = experienceService.save(experienceMapper.map(experience));
        return this.buildSuccessResponse(experienceRMapper.map(newExperience));
    }

    @Override
    @PutMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<ExperienceR>> update(
            @PathVariable("id") Integer id,
            @RequestBody ExperienceR experience
    ) throws Exception {
        Validate.notNull(experience, "No valid resource to update was provided.");
        Validate.notNull(experience.getId(), "Mandatory parameter is missing: id experience.");
        Validate.isTrue(experience.getId().equals(id), "The request's id and the body's id are different.");

        Experience experienceToUpate = experienceService.findById(experience.getId());
        Experience updatedExperience = experienceService.update(experienceMapper.map(experience, experienceToUpate));
        return this.buildSuccessResponse(experienceRMapper.map(updatedExperience));
    }

    @Override
    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<ExperienceR>> delete(@PathVariable("id") Integer id) throws Exception {
        Validate.notNull(id, "No valid parameters were provided.");

        Experience experienceToDelete = experienceService.findById(id);
        Validate.notNull(experienceToDelete, "No valid experience found with id " + id);

        experienceService.delete(experienceToDelete);
        return this.buildSuccessResponse(experienceRMapper.map(experienceToDelete));
    }
}
