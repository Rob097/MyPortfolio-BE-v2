package com.myprojects.myportfolio.core.project;

import com.myprojects.myportfolio.clients.project.ProjectR;
import com.myprojects.myportfolio.core.project.mappers.ProjectRMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/core/projects")
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRMapper projectRMapper;

    @GetMapping(path = "/{ownerId}")
    public List<ProjectR> getProjectsByUserId(@PathVariable("ownerId") Integer ownerId){
        List<Project> projects = this.projectService.getProjectsByUserId(ownerId);
        List<ProjectR> result = projects.stream().map(project -> this.projectRMapper.map(project)).collect(Collectors.toList());
        return result;
    }

}
