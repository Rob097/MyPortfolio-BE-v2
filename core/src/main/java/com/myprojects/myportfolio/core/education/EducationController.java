package com.myprojects.myportfolio.core.education;

import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.education.mappers.EducationMapper;
import com.myprojects.myportfolio.core.education.mappers.EducationRMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${core-module-basic-path}" + "/educations")
@Slf4j
public class EducationController implements IController<EducationR> {

    private final EducationServiceI educationService;

    private final EducationRMapper educationRMapper;

    private final EducationMapper educationMapper;

    private final HttpServletRequest httpServletRequest;

    public EducationController(EducationServiceI educationService, EducationRMapper educationRMapper, EducationMapper educationMapper, HttpServletRequest httpServletRequest) {
        this.educationService = educationService;
        this.educationRMapper = educationRMapper;
        this.educationMapper = educationMapper;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResources<EducationR>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view,
            Pageable pageable
    ) throws Exception {
        Specification<Education> specifications = this.defineFiltersAndStoreView(filters, view, this.httpServletRequest);

        Slice<Education> educations = educationService.findAll(specifications, pageable);

        return this.buildSuccessResponses(educations.map(educationRMapper::map));
    }

    @Override
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<EducationR>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(id, "Mandatory parameter is missing: id.");
        this.storeRequestView(view, httpServletRequest);

        Education education = educationService.findById(id);

        return this.buildSuccessResponse(educationRMapper.map(education));
    }

    @GetMapping(path="/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<EducationR>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(slug, "Mandatory parameter is missing: slug.");
        this.storeRequestView(view, httpServletRequest);

        Education education = educationService.findBySlug(slug);

        return this.buildSuccessResponse(educationRMapper.map(education));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<EducationR>> create(@RequestBody EducationR education) throws Exception {
        Validate.notNull(education, "No valid resource was provided..");

        Education newEducation = educationService.save(educationMapper.map(education));
        return this.buildSuccessResponse(educationRMapper.map(newEducation));
    }

    @Override
    @PutMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<EducationR>> update(
            @PathVariable("id") Integer id,
            @RequestBody EducationR education
    ) throws Exception {
        Validate.notNull(education, "No valid resource to update was provided.");
        Validate.notNull(education.getId(), "Mandatory parameter is missing: id education.");
        Validate.isTrue(education.getId().equals(id), "The request's id and the body's id are different.");

        Education educationToUpdate = educationService.findById(education.getId());
        Education updatedEducation = educationService.update(educationMapper.map(education, educationToUpdate));
        return this.buildSuccessResponse(educationRMapper.map(updatedEducation));
    }

    @Override
    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<EducationR>> delete(@PathVariable("id") Integer id) throws Exception {
        Validate.notNull(id, "No valid parameters were provided.");

        Education educationToDelete = educationService.findById(id);
        Validate.notNull(educationToDelete, "No valid education found with id " + id);

        educationService.delete(educationToDelete);
        return this.buildSuccessResponse(educationRMapper.map(educationToDelete));
    }
}
