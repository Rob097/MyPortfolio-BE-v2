package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.Education;
import com.myprojects.myportfolio.core.dto.EducationDto;
import com.myprojects.myportfolio.core.mappers.EducationMapper;
import com.myprojects.myportfolio.core.services.EducationServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("EducationController")
@RequestMapping("${core-module-basic-path}" + "/educations")
public class EducationController extends UserRelatedBaseController<Education, EducationDto> {

    private final EducationServiceI educationService;

    private final EducationMapper educationMapper;

    public EducationController(EducationServiceI educationService, EducationMapper educationMapper) {
        super(educationService, educationMapper);

        this.educationService = educationService;
        this.educationMapper = educationMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

    @GetMapping(path = "/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<EducationDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        Education education = educationService.findBy(findByEquals(Education.FIELDS.SLUG.getName(), slug));

        return this.buildSuccessResponse(educationMapper.mapToDto(education, view), view);
    }
}
