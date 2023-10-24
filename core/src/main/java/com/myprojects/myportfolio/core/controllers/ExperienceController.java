package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.Experience;
import com.myprojects.myportfolio.core.dto.ExperienceDto;
import com.myprojects.myportfolio.core.mappers.ExperienceMapper;
import com.myprojects.myportfolio.core.services.ExperienceServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("ExperienceController")
@RequestMapping("${core-module-basic-path}" + "/experiences")
public class ExperienceController extends UserRelatedBaseController<Experience, ExperienceDto> {

    private final ExperienceServiceI experienceService;

    private final ExperienceMapper experienceMapper;

    public ExperienceController(ExperienceServiceI experienceService, ExperienceMapper experienceMapper) {
        super(experienceService, experienceMapper);

        this.experienceService = experienceService;
        this.experienceMapper = experienceMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

    @GetMapping(path = "/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<ExperienceDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        Experience experience = experienceService.findBy(findByEquals(Experience.FIELDS.SLUG.getName(), slug));

        return this.buildSuccessResponse(experienceMapper.mapToDto(experience, view), view);
    }
}
