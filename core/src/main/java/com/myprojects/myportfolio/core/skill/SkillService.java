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
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Slice<Skill> findAll(Specification<Skill> specification, Pageable pageable){

        return this.skillRepository.findAll(specification, pageable);
    }

    public Skill findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Skill> skill = this.skillRepository.findById(id);
        return skill.orElseThrow(() -> new NoSuchElementException("Impossible to found any skill with id: " + id));
    }

    public Skill save(Skill skill){
        Validate.notNull(skill, "Mandatory parameter is missing: skill.");

        if(skill.getId()!=null) {
            Optional<Skill> actual = this.skillRepository.findById(skill.getId());
            Validate.isTrue(actual.isEmpty(), "It already exists a skill with id: " + skill.getId());
        }

        return this.skillRepository.save(skill);
    }

    public void delete(Skill skillToDelete){
        Validate.notNull(skillToDelete, "Mandatory parameter is missing: skill.");

        this.skillRepository.delete(skillToDelete);
    }

}
