package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Experience;
import com.myprojects.myportfolio.core.repositories.ExperienceRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service(value = "ExperienceService")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ExperienceService extends WithStoriesService<Experience> implements ExperienceServiceI {

    private final ExperienceRepository experienceRepository;

    private final StoryRepository storyRepository;

    public ExperienceService(ExperienceRepository experienceRepository, StoryRepository storyRepository) {
        super(experienceRepository, storyRepository);

        this.experienceRepository = experienceRepository;
        this.storyRepository = storyRepository;
    }

    /**********************/
    /*** Private Methods **/
    /**********************/
}
