package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.dao.NewDiary;
import com.myprojects.myportfolio.core.repositories.DiaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@Service(value = "newDiaryService")
public class DiaryService extends BaseService<NewDiary> implements DiaryServiceI {

    private final DiaryRepository diaryRepository;

    private final UtilsServiceI utilsService;

    public DiaryService(DiaryRepository diaryRepository, UtilsServiceI utilsService) {
        super();
        this.repository = diaryRepository;

        this.diaryRepository = diaryRepository;
        this.utilsService = utilsService;
    }

    @Override
    public NewDiary save(NewDiary diary) {

        // If the diary does not have a user, set the current logged-in user
        if (diary.getUserId() == null) {
            diary.setUser(utilsService.getCurrentLoggedInUser());
        }

        // Check if the diary already exists
        super.checkIfEntityAlreadyExists(diary.getId());

        // When creating a diary you can only create new stories. You can't connect existing stories.
        diary.getStories().removeIf(story -> story.getId() != null);

        // Connect the stories to the diary
        diary.completeRelationships();

        // Save the diary and the stories
        return diaryRepository.save(diary);
    }

    @Override
    public NewDiary update(NewDiary diary) {

        // Check if the diary does not exist
        super.checkIfEntityDoesNotExist(diary.getId());

        // When creating a diary you can only create new stories. You can't connect existing stories.
        diary.getStories().removeIf(story -> story.getId() != null);

        // Load the existing stories in order to not lose them.
        diaryRepository.findById(diary.getId()).ifPresent(existingProject -> {
            existingProject.getStories().forEach(story -> {
                diary.getStories().add(story);
            });
        });

        // Connect the stories to the diary
        diary.completeRelationships();

        // Save the diary and the stories
        return repository.save(diary);
    }

    // Remove one or more stories from a diary
    @Override
    public void removeStoriesFromDiary(Integer diaryId, Integer[] storyIds) {
        NewDiary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new RuntimeException("Diary not found"));

        for (Integer storyId : storyIds) {
            diary.getStories().removeIf(story -> story.getId().equals(storyId));
        }

        diaryRepository.save(diary);
    }

}
