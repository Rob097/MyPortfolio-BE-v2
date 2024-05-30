package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.dao.Project;
import com.myprojects.myportfolio.core.repositories.ProjectRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service(value = "ProjectService")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ProjectService extends WithStoriesService<Project> implements ProjectServiceI {

    private final ProjectRepository projectRepository;

    private final StoryRepository storyRepository;

    private final UtilsServiceI utilsService;

    public ProjectService(ProjectRepository projectRepository, StoryRepository storyRepository, UtilsServiceI utilsService) {
        super(projectRepository, storyRepository);

        this.projectRepository = projectRepository;
        this.storyRepository = storyRepository;
        this.utilsService = utilsService;
    }

    @Override
    public List<String> findSlugsByUserId(Integer userId) {
        return projectRepository.findSlugsByUserId(userId, utilsService.hasId(userId)).orElseThrow(() -> new RuntimeException("No projects found for user id: " + userId));
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
