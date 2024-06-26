package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.dao.Education;
import com.myprojects.myportfolio.core.repositories.EducationRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service(value = "EducationService")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class EducationService extends WithStoriesService<Education> implements EducationServiceI {

    private final EducationRepository educationRepository;

    private final StoryRepository storyRepository;

    private final UtilsServiceI utilsService;

    public EducationService(EducationRepository educationRepository, StoryRepository storyRepository, UtilsServiceI utilsService) {
        super(educationRepository, storyRepository);

        this.educationRepository = educationRepository;
        this.storyRepository = storyRepository;
        this.utilsService = utilsService;
    }

    @Override
    public List<String> findSlugsByUserId(Integer userId) {
        return educationRepository.findSlugsByUserId(userId, utilsService.hasId(userId)).orElseThrow(() -> new RuntimeException("No educations found for user id: " + userId));
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
