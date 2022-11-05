package com.myprojects.myportfolio.core.education;

import com.myprojects.myportfolio.clients.education.EducationQ;
import com.myprojects.myportfolio.clients.education.EducationR;
import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Synthetic;
import com.myprojects.myportfolio.core.education.mappers.EducationRMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/core/educations")
@Slf4j
public class EducationController implements IController<EducationR> {

    @Autowired
    private EducationService educationService;

    @Autowired
    private EducationRMapper educationRMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority(T(ApplicationUserPermission).USERS_READ.getName())")
    public ResponseEntity<MessageResources<EducationR>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view,
            Pageable pageable
    ) throws Exception {
        Specification<Education> specifications = this.defineFiltersAndStoreView(filters, view, this.httpServletRequest);

        Slice<Education> educations = educationService.findAll(specifications, pageable);

        return this.buildSuccessResponses(educations.map(education -> educationRMapper.map(education)));
    }

    @Override
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority(T(ApplicationUserPermission).USERS_READ.getName())")
    public ResponseEntity<MessageResource<EducationR>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(id, "Mandatory parameter is missing: id.");
        this.storeRequestView(view, httpServletRequest);

        Education education = this.educationService.findById(id);

        return this.buildSuccessResponse(educationRMapper.map(education));
    }

    @Override
    public ResponseEntity<MessageResource<EducationR>> create(EducationR resource) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<MessageResource<EducationR>> update(Integer id, EducationR resource) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<MessageResource<EducationR>> delete(Integer id) throws Exception {
        return null;
    }
}
