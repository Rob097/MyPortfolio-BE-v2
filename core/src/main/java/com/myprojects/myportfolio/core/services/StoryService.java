package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.dao.Diary;
import com.myprojects.myportfolio.core.dao.Story;
import com.myprojects.myportfolio.core.repositories.*;
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
@Service(value = "StoryService")
public class StoryService extends BaseService<Story> implements StoryServiceI {

    private final StoryRepository storyRepository;

    private final DiaryRepository diaryRepository;

    private final ProjectRepository projectRepository;

    private final EducationRepository educationRepository;

    private final ExperienceRepository experienceRepository;

    private final UtilsServiceI utilsService;

    public StoryService(StoryRepository storyRepository, DiaryRepository diaryRepository, ProjectRepository projectRepository, EducationRepository educationRepository, ExperienceRepository experienceRepository, UtilsServiceI utilsService) {
        super();
        this.repository = storyRepository;

        this.storyRepository = storyRepository;
        this.diaryRepository = diaryRepository;
        this.projectRepository = projectRepository;
        this.educationRepository = educationRepository;
        this.experienceRepository = experienceRepository;
        this.utilsService = utilsService;
    }

    @Override
    public Story findBy(Specification<Story> specification) {

        List<Story> all = this.storyRepository.findAll(specification);
        if (!all.isEmpty()) {
            return all.get(0);
        }

        return null;
    }

    @Override
    public Story save(Story story) {
        Validate.notNull(story, super.fieldMissing("story"));
        Validate.notNull(story.getDiaryId(), super.fieldMissing("Diary Id"));

        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        isDiaryOfCurrentUser(story);

        loadStoryRelations(story);

        return super.save(story);
    }

    @Override
    public Story update(Story story) {
        Validate.notNull(story, super.fieldMissing("story"));

        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        isDiaryOfCurrentUser(story);

        return super.update(story);
    }

    @Override
    public void delete(Story story) {
        Validate.notNull(story, super.fieldMissing("story"));

        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        isDiaryOfCurrentUser(story);

        super.delete(story);
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

    private void isDiaryOfCurrentUser(Story story) {
        Diary diary = diaryRepository.findById(story.getDiary().getId()).orElseThrow(() -> new EntityNotFoundException("No diary found with id: " + story.getDiary().getId()));
        if (!utilsService.hasId(diary.getUser().getId()))
            throw new IllegalArgumentException("You are not allowed to edit someone else's diary's stories.");
    }

    private void loadStoryRelations(Story story) {
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
        story.setExperiences(
                story.getExperiences().stream()
                        .map(experience -> experienceRepository.findById(experience.getId()).orElseThrow(() -> new EntityNotFoundException("No experience found with id.")))
                        .collect(Collectors.toSet())
        );
    }

}
