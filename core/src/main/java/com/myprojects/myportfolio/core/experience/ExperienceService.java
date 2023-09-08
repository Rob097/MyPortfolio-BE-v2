package com.myprojects.myportfolio.core.experience;

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
public class ExperienceService implements ExperienceServiceI {

    private final ExperienceRepository experienceRepository;

    private final UtilsServiceI utilsService;

    public ExperienceService(ExperienceRepository experienceRepository, UtilsServiceI utilsService) {
        this.experienceRepository = experienceRepository;
        this.utilsService = utilsService;
    }

    @Override
    public Slice<Experience> findAll(Specification<Experience> specification, Pageable pageable){

        return this.experienceRepository.findAll(specification, pageable);
    }

    @Override
    public Experience findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Experience> experience = this.experienceRepository.findById(id);
        return experience.orElseThrow(() -> new NoSuchElementException("Impossible to found any experience with id: " + id));
    }

    @Override
    public Experience findBySlug(String slug) {
        Validate.notNull(slug, "Mandatory parameter is missing: slug.");

        Optional<Experience> experience = this.experienceRepository.findBySlug(slug);
        return experience.orElseThrow(() -> new NoSuchElementException("Impossible to found any experience with slug: " + slug));
    }

    @Override
    public Experience save(Experience experience){
        Validate.notNull(experience, "Mandatory parameter is missing: experience.");

        if(experience.getId()!=null) {
            Optional<Experience> actual = this.experienceRepository.findById(experience.getId());
            Validate.isTrue(actual.isEmpty(), "It already exists an experience with id: " + experience.getId());
        }

        experience.setSlug(generateSlug(experience));

        return this.experienceRepository.save(experience);
    }

    @Override
    public Experience update(Experience experienceToUpdate){
        Validate.notNull(experienceToUpdate, "Mandatory parameter is missing: experience.");
        Validate.notNull(experienceToUpdate.getId(), "Mandatory parameter is missinge: id experience.");

        if(experienceToUpdate.getSlug()==null || experienceToUpdate.getSlug().isEmpty()) {
            experienceToUpdate.setSlug(generateSlug(experienceToUpdate));
        }

        return this.experienceRepository.save(experienceToUpdate);
    }

    @Override
    public void delete(Experience experienceToDelete){
        Validate.notNull(experienceToDelete, "Mandatory parameter is missing: experience.");

        this.experienceRepository.delete(experienceToDelete);
    }

    private String generateSlug(Experience experience) {
        boolean isDone = false;
        int index = 0;
        String slug;

        do {
            String appendix = index == 0 ? "" : ("-"+index);
            slug = utilsService.toSlug(experience.getTitle() + " " + experience.getCompanyName() + appendix);

            Optional<Experience> existingUser = experienceRepository.findBySlug(slug);

            if(existingUser.isPresent()) {
                index++;
            } else {
                isDone = true;
            }
        } while (!isDone);

        return slug;

    }

}
