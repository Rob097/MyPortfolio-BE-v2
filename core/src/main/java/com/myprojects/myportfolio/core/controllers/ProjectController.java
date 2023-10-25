package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.Project;
import com.myprojects.myportfolio.core.dto.ProjectDto;
import com.myprojects.myportfolio.core.mappers.ProjectMapper;
import com.myprojects.myportfolio.core.services.ProjectServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController("ProjectController")
@RequestMapping("${core-module-basic-path}" + "/projects")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ProjectController extends UserRelatedBaseController<Project, ProjectDto> {

    private final ProjectServiceI projectService;

    private final ProjectMapper projectMapper;

    public ProjectController(ProjectServiceI projectService, ProjectMapper projectMapper) {
        super(projectService, projectMapper);

        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

    @GetMapping(path = "/slug/{slug}")
    public ResponseEntity<MessageResource<ProjectDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        Project project = projectService.findBy(findByEquals(Project.FIELDS.SLUG.getName(), slug));

        return this.buildSuccessResponse(projectMapper.mapToDto(project, view), view);
    }

    @GetMapping(path = "/slugs/{userId}")
    public ResponseEntity<MessageResources<String>> getUserProjectsSlugs(
            @PathVariable("userId") Integer userId
    ) {
        Validate.notNull(userId, fieldMissing("userId"));

        return this.buildSuccessResponsesOfGenericType(projectService.findSlugsByUserId(userId), Normal.value, new ArrayList<>(), false);
    }

}
