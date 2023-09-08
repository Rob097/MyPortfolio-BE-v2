package com.myprojects.myportfolio.core.education;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

public interface EducationServiceI {

    Slice<Education> findAll(Specification<Education> specification, Pageable pageable);

    Education findById(Integer id);

    Education findBySlug(String slug);

    Education save(Education userToSave);

    Education update(Education userToUpdate);

    void delete(Education userToDelete);
    
}
