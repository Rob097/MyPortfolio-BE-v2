package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Education;
import com.myprojects.myportfolio.core.repositories.EducationRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@Service(value = "EducationService")
public class EducationService extends BaseService<Education> implements EducationServiceI {

    private final EducationRepository educationRepository;

    private final StoryRepository storyRepository;

    public EducationService(EducationRepository educationRepository, StoryRepository storyRepository) {
        super();
        this.repository = educationRepository;

        this.educationRepository = educationRepository;
        this.storyRepository = storyRepository;
    }

    @Override
    public Education findBy(Specification<Education> specification) {

        List<Education> all = this.educationRepository.findAll(specification);
        if (!all.isEmpty()) {
            return all.get(0);
        }

        return null;
    }

    /**
     * Creates a new education.
     * <p>
     * The stories that are not yet persisted will be created.
     * The stories already persisted but not present in the education that is being created will be added.
     * <p>
     *
     * @param education the education to create
     * @return the created education
     */
    @Override
    public Education save(Education education) {
        Validate.notNull(education, fieldMissing("education"));
        Validate.notNull(education.getUserId(), fieldMissing("User Id"));

        // Check if the entity already exists
        this.checkIfEntityAlreadyExists(education.getId());

        // Create stories before saving the education:
        if (education.getStories() != null && !education.getStories().isEmpty()) {
            education.getStories().forEach(story -> {
                if (story.getId() == null) {
                    storyRepository.save(story);
                }
            });
        }

        // Connect the stories to the education
        education.completeRelationships();

        return educationRepository.save(education);

    }

    /**
     * Updates an existing education.
     * <p>
     * The stories that are not yet persisted will be created.
     * The stories already persisted but not present in the education that is being updated will be added.
     * The stories already persisted and present in the education that is being updated will be left untouched.
     * <p>
     *
     * @param education the education to update
     * @return the updated education
     */
    @Override
    public Education update(Education education) {
        Validate.notNull(education, fieldMissing("education"));

        // Check if the entity does not exist
        this.checkIfEntityDoesNotExist(education.getId());

        // Create stories before saving the education:
        if (education.getStories() != null && !education.getStories().isEmpty()) {
            education.getStories().forEach(story -> {
                if (story.getId() == null) {
                    storyRepository.save(story);
                }
            });
        }

        // Load the existing stories in order to not lose them.
        educationRepository.findById(education.getId()).ifPresent(existingEducation ->
            existingEducation.getStories().forEach(story ->
                education.getStories().add(story)
            )
        );

        // Connect the stories to the education
        education.completeRelationships();

        return educationRepository.save(education);

    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
