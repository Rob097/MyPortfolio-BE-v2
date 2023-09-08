package com.myprojects.myportfolio.core.story;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

public interface StoryServiceI {

    Slice<Story> findAll(Specification<Story> specification, Pageable pageable);

    Story findById(Integer id);

    Story findBySlug(String slug);

    Story save(Story userToSave);

    Story update(Story userToUpdate);

    void delete(Story userToDelete);
    
}
