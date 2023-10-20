package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Project;
import com.myprojects.myportfolio.core.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service(value = "ProjectService")
public class ProjectService extends BaseService<Project> implements ProjectServiceI {

    private final ProjectRepository projectRepository;

    private final StoryServiceI storyService;

    public ProjectService(ProjectRepository projectRepository, StoryServiceI storyService) {
        super();
        this.repository = projectRepository;

        this.projectRepository = projectRepository;
        this.storyService = storyService;
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
    public Project save(Project project) {
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
    public Project update(Project project) {
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

        // if project.getMainStoryId() is not null, check if it's a story connected to the project:
        if (project.getMainStoryId() != null) {
            boolean isMainStoryConnectedToExperience = project.getStories().stream()
                    .anyMatch(story -> story.getId().equals(project.getMainStoryId()));
            if (!isMainStoryConnectedToExperience) {
                throw new IllegalArgumentException("The main story is not connected to the project");
            }
        }

        return projectRepository.save(project);

    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
