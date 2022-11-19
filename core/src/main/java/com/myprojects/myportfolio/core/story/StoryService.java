package com.myprojects.myportfolio.core.story;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    public Slice<Story> findAll(Specification specification, Pageable pageable){

        Slice<Story> stories = this.storyRepository.findAll(specification, pageable);

        return stories;
    }

    public Story findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Story> story = this.storyRepository.findById(id);
        return story.orElseThrow(() -> new NoSuchElementException("Impossible to found any story with id: " + id));
    }

    public Story save(Story storyToSave){
        Validate.notNull(storyToSave, "Mandatory parameter is missing: story.");

        if(storyToSave.getId()!=null) {
            Optional<Story> actual = this.storyRepository.findById(storyToSave.getId());
            Validate.isTrue(!actual.isPresent(), "It already exists a story with id: " + storyToSave.getId());
        }

        Story story = this.storyRepository.save(storyToSave);
        return story;
    }

    public Story update(Story storyToUpdate){
        Validate.notNull(storyToUpdate, "Mandatory parameter is missing: story.");
        Validate.notNull(storyToUpdate.getId(), "Mandatory parameter is missing: id story.");

        return this.storyRepository.save(storyToUpdate);
    }

    public void delete(Story storyToDelete){
        Validate.notNull(storyToDelete, "Mandatory parameter is missing: story.");

        this.storyRepository.delete(storyToDelete);
    }
    
}
