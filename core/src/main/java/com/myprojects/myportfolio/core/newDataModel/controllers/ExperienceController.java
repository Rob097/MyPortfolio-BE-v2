package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.newDataModel.dao.NewExperience;
import com.myprojects.myportfolio.core.newDataModel.dto.NewExperienceDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.ExperienceMapper;
import com.myprojects.myportfolio.core.newDataModel.services.ExperienceServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("newExperienceController")
@RequestMapping("${core-module-basic-path}" + "/new/experiences")
public class ExperienceController extends BaseController<NewExperience, NewExperienceDto> {

    private final ExperienceServiceI experienceService;

    private final ExperienceMapper experienceMapper;

    public ExperienceController(ExperienceServiceI experienceService, ExperienceMapper experienceMapper) {
        this.service = experienceService;
        this.mapper = experienceMapper;

        this.experienceService = experienceService;
        this.experienceMapper = experienceMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

    @GetMapping(path = "/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<NewExperienceDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        NewExperience experience = experienceService.findBy(findByEquals(NewExperience.FIELDS.SLUG.name(), slug));

        return this.buildSuccessResponse(experienceMapper.mapToDto(experience), view);
    }
}
