package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.dto.NewProjectDto;
import com.myprojects.myportfolio.core.newDataModel.dto.NewStoryDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.ProjectMapper;
import com.myprojects.myportfolio.core.newDataModel.services.ProjectServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @PathVariable("slug") String slug
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        NewProject project = projectService.findBy(findByEquals(NewStory.FIELDS.SLUG.name(), slug));

        return this.buildSuccessResponse(projectMapper.mapToDto(project));
    }

}
