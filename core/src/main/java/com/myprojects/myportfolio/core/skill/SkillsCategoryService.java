package com.myprojects.myportfolio.core.skill;

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
public class SkillsCategoryService {
    
    private final SkillsCategoryRepository skillsCategoryRepository;

    public SkillsCategoryService(SkillsCategoryRepository skillsCategoryRepository) {
        this.skillsCategoryRepository = skillsCategoryRepository;
    }

    public Slice<SkillsCategory> findAll(Specification<SkillsCategory> specification, Pageable pageable){

        return this.skillsCategoryRepository.findAll(specification, pageable);
    }

    public SkillsCategory findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<SkillsCategory> skillsCategory = this.skillsCategoryRepository.findById(id);
        return skillsCategory.orElseThrow(() -> new NoSuchElementException("Impossible to found any skillsCategory with id: " + id));
    }

    public SkillsCategory save(SkillsCategory skillsCategory){
        Validate.notNull(skillsCategory, "Mandatory parameter is missing: skillsCategory.");

        if(skillsCategory.getId()!=null) {
            Optional<SkillsCategory> actual = this.skillsCategoryRepository.findById(skillsCategory.getId());
            Validate.isTrue(actual.isEmpty(), "It already exists a skillsCategory with id: " + skillsCategory.getId());
        }

        return this.skillsCategoryRepository.save(skillsCategory);
    }

    public void delete(SkillsCategory skillToDelete){
        Validate.notNull(skillToDelete, "Mandatory parameter is missing: skillsCategory.");

        this.skillsCategoryRepository.delete(skillToDelete);
    }
    
}
