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

        return super.save(project);
    }

    @Override
    public NewProject update(NewProject project) {
        Validate.notNull(project, super.fieldMissing("project"));

        return super.update(project);
    }

    /**********************/
    /*** Private Methods **/
    /**********************/


}
