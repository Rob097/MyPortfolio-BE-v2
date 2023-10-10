package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.NewProject;
import com.myprojects.myportfolio.core.dto.NewProjectDto;
import com.myprojects.myportfolio.core.mappers.ProjectMapper;
import com.myprojects.myportfolio.core.services.ProjectServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("newProjectController")
@RequestMapping("${core-module-basic-path}" + "/new/projects")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ProjectController extends BaseController<NewProject, NewProjectDto> {

    private final ProjectServiceI projectService;

    private final ProjectMapper projectMapper;

    public ProjectController(ProjectServiceI projectService, ProjectMapper projectMapper) {
        this.service = projectService;
        this.mapper = projectMapper;

        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

    @GetMapping(path = "/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<NewProjectDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        NewProject project = projectService.findBy(findByEquals(NewProject.FIELDS.SLUG.name(), slug));

        return this.buildSuccessResponse(projectMapper.mapToDto(project), view);
    }

}
