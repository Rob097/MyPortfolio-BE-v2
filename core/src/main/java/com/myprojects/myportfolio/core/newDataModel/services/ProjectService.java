package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import com.myprojects.myportfolio.core.newDataModel.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service(value = "newProjectService")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ProjectService extends BaseService<NewProject> implements ProjectServiceI {

    private final ProjectRepository projectRepository;

    private final UtilsServiceI utilsService;

    private final StoryServiceI storyService;

    public ProjectService(ProjectRepository projectRepository, UtilsServiceI utilsService, StoryServiceI storyService) {
        super();
        this.repository = projectRepository;

        this.projectRepository = projectRepository;
        this.utilsService = utilsService;
        this.storyService = storyService;
    }

    @Override
    public NewProject findBy(Specification<NewProject> specification) {

        List<NewProject> all = this.projectRepository.findAll(specification);
        if (!all.isEmpty()) {
            return all.get(0);
        }

        return null;
    }

    @Override
    public NewProject save(NewProject project) {
        Validate.notNull(project, super.fieldMissing("project"));

        project.setSlug(generateSlug(project));
        if (project.getStories() != null) {
            project.getStories().forEach(story -> story.setSlug(storyService.generateSlug(story)));
        }

        return super.save(project);
    }

    @Override
    public NewProject update(NewProject project) {
        Validate.notNull(project, super.fieldMissing("project"));

        if (project.getStories() != null) {
            project.getStories().forEach(story -> story.setSlug(storyService.generateSlug(story)));
        }

        return super.update(project);
    }

    /**********************/
    /*** Private Methods **/
    /**********************/
    private String generateSlug(NewProject project) {
        boolean isDone = false;
        int index = 0;
        String slug;

        do {
            String appendix = index == 0 ? "" : ("-" + index);
            slug = utilsService.toSlug(project.getTitle() + appendix);

            Optional<NewProject> existingUser = projectRepository.findBySlug(slug);

            if (existingUser.isPresent()) {
                index++;
            } else {
                isDone = true;
            }
        } while (!isDone);

        return slug;

    }


}
