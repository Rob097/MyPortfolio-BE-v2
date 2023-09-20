package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;
import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.repositories.DiaryRepository;
import com.myprojects.myportfolio.core.newDataModel.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service(value = "newStoryService")
public class StoryService extends BaseService<NewStory> implements StoryServiceI {

    private final StoryRepository storyRepository;

    private final UtilsServiceI utilsService;

    private final DiaryRepository diaryRepository;

    private final UserServiceI userService;

    public StoryService(StoryRepository storyRepository, UtilsServiceI utilsService, DiaryRepository diaryRepository, UserServiceI userService) {
        super();
        this.repository = storyRepository;

        this.storyRepository = storyRepository;
        this.utilsService = utilsService;
        this.diaryRepository = diaryRepository;
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

        story.setSlug(generateSlug(story));

        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        isDiaryOfCurrentUser(story);

        return super.save(story);
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
    private String generateSlug(NewStory story) {
        boolean isDone = false;
        int index = 0;
        String slug;

        do {
            String appendix = index == 0 ? "" : ("-" + index);
            slug = utilsService.toSlug(story.getTitle() + appendix);

            Optional<NewStory> existingUser = storyRepository.findBySlug(slug);

            if (existingUser.isPresent()) {
                index++;
            } else {
                isDone = true;
            }
        } while (!isDone);

        return slug;

    }

    private void isDiaryOfCurrentUser(NewStory story) {
        NewDiary diary = diaryRepository.findById(story.getDiary().getId()).orElseThrow(() -> new EntityNotFoundException("No diary found with id: " + story.getDiary().getId()));
        if (!userService.hasId(diary.getUser().getId()))
            throw new IllegalArgumentException("You are not allowed to edit someone else's diary's stories.");
    }

}
