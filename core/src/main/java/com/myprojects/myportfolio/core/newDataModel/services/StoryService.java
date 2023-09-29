package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;
import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.repositories.DiaryRepository;
import com.myprojects.myportfolio.core.newDataModel.repositories.EducationRepository;
import com.myprojects.myportfolio.core.newDataModel.repositories.ProjectRepository;
import com.myprojects.myportfolio.core.newDataModel.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service(value = "newStoryService")
public class StoryService extends BaseService<NewStory> implements StoryServiceI {

    private final StoryRepository storyRepository;

    private final DiaryRepository diaryRepository;

    private final ProjectRepository projectRepository;

    private final EducationRepository educationRepository;

    private final UserServiceI userService;

    public StoryService(StoryRepository storyRepository, DiaryRepository diaryRepository, ProjectRepository projectRepository, EducationRepository educationRepository, UserServiceI userService) {
        super();
        this.repository = storyRepository;

        this.storyRepository = storyRepository;
        this.diaryRepository = diaryRepository;
        this.projectRepository = projectRepository;
        this.educationRepository = educationRepository;
        this.userService = userService;
    }

    @Override
    public NewStory findBy(Specification<NewStory> specification) {

        List<NewStory> all = this.storyRepository.findAll(specification);
        if (!all.isEmpty()) {
            return all.get(0);
        }

        return null;
    }

    @Override
    public NewStory save(NewStory story) {
        Validate.notNull(story, super.fieldMissing("story"));

        // TODO: Reactivate once tests are finished.
        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        // isDiaryOfCurrentUser(story);
        loadStoryRelations(story);

        return super.save(story);
    }

    @Override
    public NewStory update(NewStory story) {
        Validate.notNull(story, super.fieldMissing("story"));

        // TODO: Reactivate once tests are finished.
        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        // isDiaryOfCurrentUser(story);

        return super.update(story);
    }

    @Override
    public void delete(NewStory story) {
        Validate.notNull(story, super.fieldMissing("story"));

        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        isDiaryOfCurrentUser(story);

        super.delete(story);
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

    private void isDiaryOfCurrentUser(NewStory story) {
        NewDiary diary = diaryRepository.findById(story.getDiary().getId()).orElseThrow(() -> new EntityNotFoundException("No diary found with id: " + story.getDiary().getId()));
        if (!userService.hasId(diary.getUser().getId()))
            throw new IllegalArgumentException("You are not allowed to edit someone else's diary's stories.");
    }

    private void loadStoryRelations(NewStory story) {
        story.setDiary(
                diaryRepository.findById(story.getDiary().getId()).orElseThrow(() -> new EntityNotFoundException("No diary found with id."))
        );
        story.setProjects(
                story.getProjects().stream()
                        .map(project -> projectRepository.findById(project.getId()).orElseThrow(() -> new EntityNotFoundException("No project found with id.")))
                        .collect(Collectors.toSet())
        );
        story.setEducations(
                story.getEducations().stream()
                        .map(education -> educationRepository.findById(education.getId()).orElseThrow(() -> new EntityNotFoundException("No education found with id.")))
                        .collect(Collectors.toSet())
        );
    }

}
