package com.myprojects.myportfolio.core.story;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.diary.DiaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class StoryService implements StoryServiceI {

    private final StoryRepository storyRepository;

    private final UtilsServiceI utilsService;

    private final DiaryRepository diaryRepository;

    public StoryService(StoryRepository storyRepository, UtilsServiceI utilsService, DiaryRepository diaryRepository) {
        this.storyRepository = storyRepository;
        this.utilsService = utilsService;
        this.diaryRepository = diaryRepository;
    }

    @Override
    public Slice<Story> findAll(Specification<Story> specification, Pageable pageable){

        return this.storyRepository.findAll(specification, pageable);
    }

    @Override
    public Story findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Story> story = this.storyRepository.findById(id);
        return story.orElseThrow(() -> new NoSuchElementException("Impossible to found any story with id: " + id));
    }

    @Override
    public Story findBySlug(String slug) {
        Validate.notNull(slug, "Mandatory parameter is missing: slug.");

        Optional<Story> story = this.storyRepository.findBySlug(slug);
        return story.orElseThrow(() -> new NoSuchElementException("Impossible to found any story with slug: " + slug));
    }

    @Override
    public Story save(Story storyToSave){
        Validate.notNull(storyToSave, "Mandatory parameter is missing: story.");
        Validate.notNull(storyToSave.getDiary(), "Mandatory parameter is missing: diary.");

        if(storyToSave.getId()!=null) {
            Optional<Story> actual = this.storyRepository.findById(storyToSave.getId());
            Validate.isTrue(actual.isEmpty(), "It already exists a story with id: " + storyToSave.getId());
        }

        if(storyToSave.getEntryDateTime()==null) {
            storyToSave.setEntryDateTime(LocalDateTime.now());
        }

        storyToSave.setSlug(generateSlug(storyToSave));

        // TODO: Important, we need to check that the diary_id passed is actually a diary of the current user.
        // Otherwise, potentially anyone could add a story to the diary of other users.
        Diary diary = diaryRepository.findById(storyToSave.getDiary().getId()).orElse(null);
        if(diary != null)
            diary.addStory(storyToSave);

        return this.storyRepository.save(storyToSave);
    }

    @Override
    public Story update(Story storyToUpdate){
        Validate.notNull(storyToUpdate, "Mandatory parameter is missing: story.");
        Validate.notNull(storyToUpdate.getId(), "Mandatory parameter is missing: id story.");

        if(storyToUpdate.getSlug()==null || storyToUpdate.getSlug().isEmpty()) {
            storyToUpdate.setSlug(generateSlug(storyToUpdate));
        }

        return this.storyRepository.save(storyToUpdate);
    }

    @Override
    public void delete(Story storyToDelete){
        Validate.notNull(storyToDelete, "Mandatory parameter is missing: story.");

        // TODO: Important, we need to check that the diary_id passed is actually a diary of the current user.
        // Otherwise, potentially anyone could add a story to the diary of other users.
        Diary diary = diaryRepository.findById(storyToDelete.getDiary().getId()).orElse(null);
        if(diary != null)
            diary.removeStory(storyToDelete);

        this.storyRepository.delete(storyToDelete);
    }

    private String generateSlug(Story story) {
        boolean isDone = false;
        int index = 0;
        String slug;

        do {
            String appendix = index == 0 ? "" : ("-"+index);
            slug = utilsService.toSlug(story.getTitle() + appendix);

            Optional<Story> existingUser = storyRepository.findBySlug(slug);

            if(existingUser.isPresent()) {
                index++;
            } else {
                isDone = true;
            }
        } while (!isDone);

        return slug;

    }
    
}
