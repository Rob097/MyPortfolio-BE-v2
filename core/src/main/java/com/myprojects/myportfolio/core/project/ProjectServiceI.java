package com.myprojects.myportfolio.core.project;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

public interface ProjectServiceI {

    Slice<Project> findAll(Specification<Project> specification, Pageable pageable);

    Project findById(Integer id);

    Project findBySlug(String slug);

    Project save(Project userToSave);

    Project update(Project userToUpdate);

    void delete(Project userToDelete);
    
}
