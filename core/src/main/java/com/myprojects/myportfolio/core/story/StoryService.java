package com.myprojects.myportfolio.core.story;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class StoryService implements StoryServiceI {

    private final StoryRepository storyRepository;

    private final UtilsServiceI utilsService;

    public StoryService(StoryRepository storyRepository, UtilsServiceI utilsService) {
        this.storyRepository = storyRepository;
        this.utilsService = utilsService;
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

        if(storyToSave.getId()!=null) {
            Optional<Story> actual = this.storyRepository.findById(storyToSave.getId());
            Validate.isTrue(actual.isEmpty(), "It already exists a story with id: " + storyToSave.getId());
        }

        storyToSave.setSlug(generateSlug(storyToSave));

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
