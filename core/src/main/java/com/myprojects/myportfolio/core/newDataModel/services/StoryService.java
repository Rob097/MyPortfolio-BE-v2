package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.repositories.StoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@Service(value = "newStoryService")
public class StoryService extends BaseService<NewStory> implements StoryServiceI {

    private final StoryRepository storyRepository;

    private final UtilsServiceI utilsService;

    public StoryService(StoryRepository storyRepository, UtilsServiceI utilsService) {
        super();
        this.repository = storyRepository;

        this.storyRepository = storyRepository;
        this.utilsService = utilsService;
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

        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        // isDiaryOfCurrentUser(story);

        return super.save(story);
    }

    @Override
    public void delete(NewStory story) {
        Validate.notNull(story, super.fieldMissing("story"));

        // Important, we need to check that the diary_id passed is actually a diary of the current user.
        // isDiaryOfCurrentUser(story);

        super.delete(story);
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

    // TODO: This check is probably more appropriate in the controller.
    /*private void isDiaryOfCurrentUser(NewStory story) {
        NewDiary diary = diaryRepository.findById(story.getDiary().getId()).orElseThrow(() -> new EntityNotFoundException("No diary found with id: " + story.getDiary().getId()));
        if (!userService.hasId(diary.getUser().getId()))
            throw new IllegalArgumentException("You are not allowed to edit someone else's diary's stories.");
    }*/

}
