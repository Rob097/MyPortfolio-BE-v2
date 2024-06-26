package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.dao.Experience;
import com.myprojects.myportfolio.core.repositories.ExperienceRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service(value = "ExperienceService")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ExperienceService extends WithStoriesService<Experience> implements ExperienceServiceI {

    private final ExperienceRepository experienceRepository;

    private final StoryRepository storyRepository;

    private final UtilsServiceI utilsService;

    public ExperienceService(ExperienceRepository experienceRepository, StoryRepository storyRepository, UtilsServiceI utilsService) {
        super(experienceRepository, storyRepository);

        this.experienceRepository = experienceRepository;
        this.storyRepository = storyRepository;
        this.utilsService = utilsService;
    }

    @Override
    public List<String> findSlugsByUserId(Integer userId) {
        return experienceRepository.findSlugsByUserId(userId, utilsService.hasId(userId)).orElseThrow(() -> new RuntimeException("No experiences found for user id: " + userId));
    }

    /**********************/
    /*** Private Methods **/
    /**********************/
}
