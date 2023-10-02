package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.newDataModel.dao.NewEducation;
import com.myprojects.myportfolio.core.newDataModel.repositories.EducationRepository;
import com.myprojects.myportfolio.core.newDataModel.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@Service(value = "newEducationService")
public class EducationService extends BaseService<NewEducation> implements EducationServiceI {

    private final EducationRepository educationRepository;

    private final StoryRepository storyRepository;

    public EducationService(EducationRepository educationRepository, StoryRepository storyRepository) {
        super();
        this.repository = educationRepository;

        this.educationRepository = educationRepository;
        this.storyRepository = storyRepository;
    }

    @Override
    public NewEducation findBy(Specification<NewEducation> specification) {

        List<NewEducation> all = this.educationRepository.findAll(specification);
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
    public NewEducation save(NewEducation education) {
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
    public NewEducation update(NewEducation education) {
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
