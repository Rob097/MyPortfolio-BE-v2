package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Experience;
import com.myprojects.myportfolio.core.repositories.ExperienceRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service(value = "ExperienceService")
public class ExperienceService extends BaseService<Experience> implements ExperienceServiceI {

    private final ExperienceRepository experienceRepository;

    private final StoryRepository storyRepository;

    public ExperienceService(ExperienceRepository experienceRepository, StoryRepository storyRepository) {
        super();
        this.repository = experienceRepository;

        this.experienceRepository = experienceRepository;
        this.storyRepository = storyRepository;
    }

    /**
     * Creates a new experience.
     * <p>
     * The stories that are not yet persisted will be created.
     * The stories already persisted but not present in the experience that is being created will be added.
     * <p>
     *
     * @param experience the experience to create
     * @return the created experience
     */
    @Override
    public Experience save(Experience experience) {
        Validate.notNull(experience, fieldMissing("experience"));
        Validate.notNull(experience.getUserId(), fieldMissing("User Id"));

        // Check if the entity already exists
        this.checkIfEntityAlreadyExists(experience.getId());

        // Create stories before saving the experience:
        if (experience.getStories() != null && !experience.getStories().isEmpty()) {
            experience.getStories().forEach(story -> {
                if (story.getId() == null) {
                    storyRepository.save(story);
                }
            });
        }

        // Connect the stories to the experience
        experience.completeRelationships();

        return experienceRepository.save(experience);

    }

    /**
     * Updates an existing experience.
     * <p>
     * The stories that are not yet persisted will be created.
     * The stories already persisted but not present in the experience that is being updated will be added.
     * The stories already persisted and present in the experience that is being updated will be left untouched.
     * <p>
     *
     * @param experience the experience to update
     * @return the updated experience
     */
    @Override
    public Experience update(Experience experience) {
        Validate.notNull(experience, fieldMissing("experience"));

        // Check if the entity does not exist
        this.checkIfEntityDoesNotExist(experience.getId());

        // Create stories before saving the experience:
        if (experience.getStories() != null && !experience.getStories().isEmpty()) {
            experience.getStories().forEach(story -> {
                if (story.getId() == null) {
                    storyRepository.save(story);
                }
            });
        }

        // Load the existing stories in order to not lose them.
        experienceRepository.findById(experience.getId()).ifPresent(existingExperience ->
                existingExperience.getStories().forEach(story ->
                        experience.getStories().add(story)
                )
        );

        // Connect the stories to the experience
        experience.completeRelationships();

        // if experience.getMainStoryId() is not null, check if it's a story connected to the experience:
        if (experience.getMainStoryId() != null) {
            boolean isMainStoryConnectedToExperience = experience.getStories().stream()
                    .anyMatch(story -> story.getId().equals(experience.getMainStoryId()));
            if (!isMainStoryConnectedToExperience) {
                throw new IllegalArgumentException("The main story is not connected to the experience");
            }
        }

        return experienceRepository.save(experience);

    }

    /**********************/
    /*** Private Methods **/
    /**********************/
}
