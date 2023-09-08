package com.myprojects.myportfolio.core.project;

import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.project.ProjectR;
import com.myprojects.myportfolio.core.project.mappers.ProjectMapper;
import com.myprojects.myportfolio.core.project.mappers.ProjectRMapper;
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
@RequestMapping("${core-module-basic-path}" + "/projects")
@Slf4j
public class ProjectController implements IController<ProjectR> {

    private final ProjectServiceI projectService;

    private final ProjectRMapper projectRMapper;

    private final ProjectMapper projectMapper;

    private final HttpServletRequest httpServletRequest;

    public ProjectController(ProjectServiceI projectService, ProjectRMapper projectRMapper, ProjectMapper projectMapper, HttpServletRequest httpServletRequest) {
        this.projectService = projectService;
        this.projectRMapper = projectRMapper;
        this.projectMapper = projectMapper;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResources<ProjectR>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view,
            Pageable pageable
    ) throws Exception {
        Specification<Project> specifications = this.defineFiltersAndStoreView(filters, view, this.httpServletRequest);

        Slice<Project> projects = projectService.findAll(specifications, pageable);

        return this.buildSuccessResponses(projects.map(projectRMapper::map));
    }

    @Override
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<ProjectR>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(id, "Mandatory parameter is missing: id.");
        this.storeRequestView(view, httpServletRequest);

        Project project = projectService.findById(id);

        return this.buildSuccessResponse(projectRMapper.map(project));
    }

    @GetMapping(path = "/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<ProjectR>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(slug, "Mandatory parameter is missing: slug.");
        this.storeRequestView(view, httpServletRequest);

        Project project = projectService.findBySlug(slug);

        return this.buildSuccessResponse(projectRMapper.map(project));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<ProjectR>> create(@RequestBody ProjectR project) throws Exception {
        Validate.notNull(project, "No valid resource was provided..");

        Project newProject = projectService.save(projectMapper.map(project));
        return this.buildSuccessResponse(projectRMapper.map(newProject));
    }

    @Override
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<ProjectR>> update(
            @PathVariable("id") Integer id,
            @RequestBody ProjectR project
    ) throws Exception {
        Validate.notNull(project, "No valid resource to update was provided.");
        Validate.notNull(project.getId(), "Mandatory parameter is missing: id project.");
        Validate.isTrue(project.getId().equals(id), "The request's id and the body's id are different.");

        Project projectToUpate = projectService.findById(project.getId());
        Project updatedProject = projectService.update(projectMapper.map(project, projectToUpate));
        return this.buildSuccessResponse(projectRMapper.map(updatedProject));
    }

    @Override
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<ProjectR>> delete(@PathVariable("id") Integer id) throws Exception {
        Validate.notNull(id, "No valid parameters were provided.");

        Project projectToDelete = projectService.findById(id);
        Validate.notNull(projectToDelete, "No valid project found with id " + id);

        projectService.delete(projectToDelete);
        return this.buildSuccessResponse(projectRMapper.map(projectToDelete));
    }

}
