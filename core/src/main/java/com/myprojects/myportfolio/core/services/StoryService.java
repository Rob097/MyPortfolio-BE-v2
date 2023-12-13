package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.dao.*;
import com.myprojects.myportfolio.core.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<String> findSlugsByUserId(Integer userId) {
        return storyRepository.findSlugsByUserId(userId).orElseThrow(() -> new RuntimeException("No stories found for user id: " + userId));
    }

    @Override
    public Story save(Story story) {
        Validate.notNull(story, super.fieldMissing("story"));
        Validate.notNull(story.getDiaryId(), super.fieldMissing("Diary Id"));

        loadStoryRelations(story);

        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        isDiaryOfCurrentUser(story);
        if (story.getProject() != null && story.getProject().getId() != null)
            isProjectOfStoryUser(story);
        if (story.getEducation() != null && story.getEducation().getId() != null)
            isEducationOfStoryUser(story);
        if (story.getExperience() != null && story.getExperience().getId() != null)
            isExperienceOfStoryUser(story);

        return super.save(story);
    }

    @Override
    public Story update(Story story) {
        Validate.notNull(story, super.fieldMissing("story"));

        loadStoryRelations(story);

        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        isDiaryOfCurrentUser(story);
        if (story.getProject() != null && story.getProject().getId() != null)
            isProjectOfStoryUser(story);
        if (story.getEducation() != null && story.getEducation().getId() != null)
            isEducationOfStoryUser(story);
        if (story.getExperience() != null && story.getExperience().getId() != null)
            isExperienceOfStoryUser(story);

        // Manage relevant sections:
        // for each relevant section, set story_id to this story's id
        if(story.getRelevantSections() != null && !story.getRelevantSections().isEmpty()) {
            story.getRelevantSections().forEach(section -> section.setStory(story));
        }

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

    private void isProjectOfStoryUser(Story story) {
        Project project = projectRepository.findById(story.getProject().getId()).orElseThrow(() -> new EntityNotFoundException("No project found with id: " + story.getProject().getId()));
        if (!project.getUser().getId().equals(story.getDiary().getUserId()))
            throw new IllegalArgumentException("You are not allowed to connect the story to a someone else's project.");
    }

    private void isEducationOfStoryUser(Story story) {
        Education education = educationRepository.findById(story.getEducation().getId()).orElseThrow(() -> new EntityNotFoundException("No education found with id: " + story.getEducation().getId()));
        if (!education.getUser().getId().equals(story.getDiary().getUserId()))
            throw new IllegalArgumentException("You are not allowed to connect the story to a someone else's education.");
    }

    private void isExperienceOfStoryUser(Story story) {
        Experience experience = experienceRepository.findById(story.getExperience().getId()).orElseThrow(() -> new EntityNotFoundException("No experience found with id: " + story.getExperience().getId()));
        if (!experience.getUser().getId().equals(story.getDiary().getUserId()))
            throw new IllegalArgumentException("You are not allowed to connect the story to a someone else's experience.");
    }

    private void loadStoryRelations(Story story) {
        story.setDiary(
                diaryRepository.findById(story.getDiary().getId()).orElseThrow(() -> new EntityNotFoundException("No diary found with id."))
        );
        story.setProject(
                story.getProject() != null && story.getProject().getId() != null ? projectRepository.findById(story.getProject().getId()).orElseThrow(() -> new EntityNotFoundException("No project found with id.")) : null
        );
        story.setEducation(
                story.getEducation() != null && story.getEducation().getId() != null ? educationRepository.findById(story.getEducation().getId()).orElseThrow(() -> new EntityNotFoundException("No education found with id.")) : null
        );
        story.setExperience(
                story.getExperience() != null && story.getExperience().getId() != null ? experienceRepository.findById(story.getExperience().getId()).orElseThrow(() -> new EntityNotFoundException("No experience found with id.")) : null
        );
    }

}
