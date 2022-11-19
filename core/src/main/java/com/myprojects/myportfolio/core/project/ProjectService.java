package com.myprojects.myportfolio.core.project;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Slice<Project> findAll(Specification specification, Pageable pageable){

        Slice<Project> projects = projectRepository.findAll(specification, pageable);

        return projects;
    }

    public Project findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Project> project = projectRepository.findById(id);
        return project.orElseThrow(() -> new NoSuchElementException("Impossible to found any project with id: " + id));
    }

    public Project save(Project project){
        Validate.notNull(project, "Mandatory parameter is missing: project.");

        if(project.getId()!=null) {
            Optional<Project> actual = projectRepository.findById(project.getId());
            Validate.isTrue(!actual.isPresent(), "It already exists a project with id: " + project.getId());
        }

        Project createdEducation = projectRepository.save(project);
        return createdEducation;
    }

    public Project update(Project projectToUpdate){
        Validate.notNull(projectToUpdate, "Mandatory parameter is missing: project.");
        Validate.notNull(projectToUpdate.getId(), "Mandatory parameter is missing: id project.");

        return projectRepository.save(projectToUpdate);
    }

    public void delete(Project projectToDelete){
        Validate.notNull(projectToDelete, "Mandatory parameter is missing: project.");

        this.projectRepository.delete(projectToDelete);
    }

}
