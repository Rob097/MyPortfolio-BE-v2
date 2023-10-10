package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.NewProject;
import com.myprojects.myportfolio.core.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@Service(value = "newProjectService")
public class ProjectService extends BaseService<NewProject> implements ProjectServiceI {

    private final ProjectRepository projectRepository;

    private final StoryServiceI storyService;

    public ProjectService(ProjectRepository projectRepository, StoryServiceI storyService) {
        super();
        this.repository = projectRepository;

        this.projectRepository = projectRepository;
        this.storyService = storyService;
    }

    @Override
    public NewProject findBy(Specification<NewProject> specification) {

        List<NewProject> all = this.projectRepository.findAll(specification);
        if (!all.isEmpty()) {
            return all.get(0);
        }

        return null;
    }

    /**
     * Creates a new project.
     * <p>
     * The stories that are not yet persisted will be created.
     * The stories already persisted but not present in the project that is being created will be added.
     * <p>
     *
     * @param project the project to create
     * @return the created project
     */
    @Override
    public NewProject save(NewProject project) {
        Validate.notNull(project, fieldMissing("project"));
        Validate.notNull(project.getUserId(), fieldMissing("User Id"));

        // Check if the entity already exists
        this.checkIfEntityAlreadyExists(project.getId());

        // Create stories before saving the project:
        if (project.getStories() != null && !project.getStories().isEmpty()) {
            project.getStories().forEach(story -> {
                if (story.getId() == null) {
                    storyService.save(story);
                }
            });
        }

        // Connect the stories to the project
        project.completeRelationships();

        return projectRepository.save(project);

    }

    /**
     * Updates an existing project.
     * <p>
     * The stories that are not yet persisted will be created.
     * The stories already persisted but not present in the project that is being updated will be added.
     * The stories already persisted and present in the project that is being updated will be left untouched.
     * <p>
     *
     * @param project the project to update
     * @return the updated project
     */
    @Override
    public NewProject update(NewProject project) {
        Validate.notNull(project, fieldMissing("project"));

        // Check if the entity does not exist
        this.checkIfEntityDoesNotExist(project.getId());

        // Create stories before saving the project:
        if (project.getStories() != null && !project.getStories().isEmpty()) {
            project.getStories().forEach(story -> {
                if (story.getId() == null) {
                    storyService.save(story);
                }
            });
        }

        // Load the existing stories in order to not lose them (or change them).
        projectRepository.findById(project.getId()).ifPresent(existingProject ->
                existingProject.getStories().forEach(existingStory ->
                        project.getStories().add(existingStory)
                )
        );

        // Connect the stories to the project
        project.completeRelationships();

        return projectRepository.save(project);

    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
