package com.myprojects.myportfolio.core.project;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProjectService implements ProjectServiceI {

    private final ProjectRepository projectRepository;

    private final UtilsServiceI utilsService;

    public ProjectService(ProjectRepository projectRepository, UtilsServiceI utilsService) {
        this.projectRepository = projectRepository;
        this.utilsService = utilsService;
    }

    @Override
    public Slice<Project> findAll(Specification<Project> specification, Pageable pageable){

        return projectRepository.findAll(specification, pageable);
    }

    @Override
    public Project findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Project> project = projectRepository.findById(id);
        return project.orElseThrow(() -> new NoSuchElementException("Impossible to found any project with id: " + id));
    }

    @Override
    public Project findBySlug(String slug) {
        Validate.notNull(slug, "Mandatory parameter is missing: slug.");

        Optional<Project> project = this.projectRepository.findBySlug(slug);
        return project.orElseThrow(() -> new NoSuchElementException("Impossible to found any project with slug: " + slug));
    }

    @Override
    public Project save(Project project){
        Validate.notNull(project, "Mandatory parameter is missing: project.");

        if(project.getId()!=null) {
            Optional<Project> actual = projectRepository.findById(project.getId());
            Validate.isTrue(actual.isEmpty(), "It already exists a project with id: " + project.getId());
        }

        project.setSlug(generateSlug(project));

        return projectRepository.save(project);
    }

    @Override
    public Project update(Project projectToUpdate){
        Validate.notNull(projectToUpdate, "Mandatory parameter is missing: project.");
        Validate.notNull(projectToUpdate.getId(), "Mandatory parameter is missing: id project.");

        if(projectToUpdate.getSlug()==null || projectToUpdate.getSlug().isEmpty()) {
            projectToUpdate.setSlug(generateSlug(projectToUpdate));
        }

        return projectRepository.save(projectToUpdate);
    }

    @Override
    public void delete(Project projectToDelete){
        Validate.notNull(projectToDelete, "Mandatory parameter is missing: project.");

        this.projectRepository.delete(projectToDelete);
    }

    private String generateSlug(Project project) {
        boolean isDone = false;
        int index = 0;
        String slug;

        do {
            String appendix = index == 0 ? "" : ("-"+index);
            slug = utilsService.toSlug(project.getTitle() + appendix);

            Optional<Project> existingUser = projectRepository.findBySlug(slug);

            if(existingUser.isPresent()) {
                index++;
            } else {
                isDone = true;
            }
        } while (!isDone);

        return slug;

    }

}
