package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Project;
import com.myprojects.myportfolio.core.dao.Story;
import com.myprojects.myportfolio.core.repositories.ProjectRepository;
import com.myprojects.myportfolio.core.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service(value = "ProjectService")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ProjectService extends WithStoriesService<Project> implements ProjectServiceI {

    private final ProjectRepository projectRepository;

    private final StoryRepository storyRepository;

    public ProjectService(ProjectRepository projectRepository, StoryRepository storyRepository) {
        super(projectRepository, storyRepository);

        this.projectRepository = projectRepository;
        this.storyRepository = storyRepository;
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
