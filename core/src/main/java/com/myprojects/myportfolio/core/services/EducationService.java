package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Education;
import com.myprojects.myportfolio.core.repositories.EducationRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service(value = "EducationService")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class EducationService extends WithStoriesService<Education> implements EducationServiceI {

    private final EducationRepository educationRepository;

    private final StoryRepository storyRepository;

    public EducationService(EducationRepository educationRepository, StoryRepository storyRepository) {
        super(educationRepository, storyRepository);

        this.educationRepository = educationRepository;
        this.storyRepository = storyRepository;
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
