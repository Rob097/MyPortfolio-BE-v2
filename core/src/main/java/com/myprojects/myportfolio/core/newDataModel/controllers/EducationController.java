package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.newDataModel.dao.NewEducation;
import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.dto.NewEducationDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.EducationMapper;
import com.myprojects.myportfolio.core.newDataModel.services.EducationServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("newEducationController")
@RequestMapping("${core-module-basic-path}" + "/new/educations")
public class EducationController extends BaseController<NewEducation, NewEducationDto> {

    private final EducationServiceI educationService;

    private final EducationMapper educationMapper;

    public EducationController(EducationServiceI educationService, EducationMapper educationMapper) {
        this.service = educationService;
        this.mapper = educationMapper;

        this.educationService = educationService;
        this.educationMapper = educationMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

    @GetMapping(path = "/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<NewEducationDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        NewEducation education = educationService.findBy(findByEquals(NewStory.FIELDS.SLUG.name(), slug));

        return this.buildSuccessResponse(educationMapper.mapToDto(education), view);
    }
}
