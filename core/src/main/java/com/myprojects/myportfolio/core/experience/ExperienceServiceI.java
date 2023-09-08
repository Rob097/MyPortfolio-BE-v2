package com.myprojects.myportfolio.core.experience;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

public interface ExperienceServiceI {

    Slice<Experience> findAll(Specification<Experience> specification, Pageable pageable);

    Experience findById(Integer id);

    Experience findBySlug(String slug);

    Experience save(Experience userToSave);

    Experience update(Experience userToUpdate);

    void delete(Experience userToDelete);
    
}
