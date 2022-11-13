package com.myprojects.myportfolio.core.experience;

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
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    public Slice<Experience> findAll(Specification specification, Pageable pageable){

        Slice<Experience> experiences = this.experienceRepository.findAll(specification, pageable);

        return experiences;
    }

    public Experience findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Experience> experience = this.experienceRepository.findById(id);
        return experience.orElseThrow(() -> new NoSuchElementException("Impossible to found any experience with id: " + id));
    }

    public Experience save(Experience experience){
        Validate.notNull(experience, "Mandatory parameter is missing: experience.");

        if(experience.getId()!=null) {
            Optional<Experience> actual = this.experienceRepository.findById(experience.getId());
            Validate.isTrue(!actual.isPresent(), "It already exists an experience with id: " + experience.getId());
        }

        Experience createdExperience = this.experienceRepository.save(experience);
        return createdExperience;
    }

    public Experience update(Experience experienceToUpdate){
        Validate.notNull(experienceToUpdate, "Mandatory parameter is missing: experience.");
        Validate.notNull(experienceToUpdate.getId(), "Mandatory parameter is missinge: id experience.");

        return this.experienceRepository.save(experienceToUpdate);
    }

    public void delete(Experience experienceToDelete){
        Validate.notNull(experienceToDelete, "Mandatory parameter is missing: experience.");

        this.experienceRepository.delete(experienceToDelete);
    }

}
