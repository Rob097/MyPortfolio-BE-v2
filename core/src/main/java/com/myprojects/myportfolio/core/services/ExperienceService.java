package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.NewExperience;
import com.myprojects.myportfolio.core.repositories.ExperienceRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@Service(value = "newExperienceService")
public class ExperienceService extends BaseService<NewExperience> implements ExperienceServiceI {

    private final ExperienceRepository experienceRepository;

    private final StoryRepository storyRepository;

    public ExperienceService(ExperienceRepository experienceRepository, StoryRepository storyRepository) {
        super();
        this.repository = experienceRepository;

        this.experienceRepository = experienceRepository;
        this.storyRepository = storyRepository;
    }

    @Override
    public NewExperience findBy(Specification<NewExperience> specification) {

        List<NewExperience> all = this.experienceRepository.findAll(specification);
        if (!all.isEmpty()) {
            return all.get(0);
        }

        return null;
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
    public NewExperience save(NewExperience experience) {
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
    public NewExperience update(NewExperience experience) {
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

        return experienceRepository.save(experience);

    }

    /**********************/
    /*** Private Methods **/
    /**********************/
}
