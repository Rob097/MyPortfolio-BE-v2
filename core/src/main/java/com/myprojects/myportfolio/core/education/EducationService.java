package com.myprojects.myportfolio.core.education;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class EducationService implements EducationServiceI {

    private final EducationRepository educationRepository;

    private final UtilsServiceI utilsService;

    public EducationService(EducationRepository educationRepository, UtilsServiceI utilsService) {
        this.educationRepository = educationRepository;
        this.utilsService = utilsService;
    }

    @Override
    public Slice<Education> findAll(Specification<Education> specification, Pageable pageable){

        return educationRepository.findAll(specification, pageable);
    }

    @Override
    public Education findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Education> education = educationRepository.findById(id);
        return education.orElseThrow(() -> new NoSuchElementException("Impossible to found any education with id: " + id));
    }

    @Override
    public Education findBySlug(String slug) {
        Validate.notNull(slug, "Mandatory parameter is missing: slug.");

        Optional<Education> education = this.educationRepository.findBySlug(slug);
        return education.orElseThrow(() -> new NoSuchElementException("Impossible to found any education with slug: " + slug));
    }

    @Override
    public Education save(Education education){
        Validate.notNull(education, "Mandatory parameter is missing: education.");

        if(education.getId()!=null) {
            Optional<Education> actual = educationRepository.findById(education.getId());
            Validate.isTrue(actual.isEmpty(), "It already exists an education with id: " + education.getId());
        }

        if(education.getEntryDateTime()==null) {
            education.setEntryDateTime(LocalDateTime.now());
        }

        education.setSlug(generateSlug(education));

        return educationRepository.save(education);
    }

    @Override
    public Education update(Education educationToUpdate){
        Validate.notNull(educationToUpdate, "Mandatory parameter is missing: education.");
        Validate.notNull(educationToUpdate.getId(), "Mandatory parameter is missing: id education.");

        if(educationToUpdate.getSlug()==null || educationToUpdate.getSlug().isEmpty()) {
            educationToUpdate.setSlug(generateSlug(educationToUpdate));
        }

        return educationRepository.save(educationToUpdate);
    }

    @Override
    public void delete(Education educationToDelete){
        Validate.notNull(educationToDelete, "Mandatory parameter is missing: education.");

        this.educationRepository.delete(educationToDelete);
    }

    private String generateSlug(Education education) {
        boolean isDone = false;
        int index = 0;
        String slug;

        do {
            String appendix = index == 0 ? "" : ("-"+index);
            slug = utilsService.toSlug(education.getField() + " " + education.getSchool() + appendix);

            Optional<Education> existingUser = educationRepository.findBySlug(slug);

            if(existingUser.isPresent()) {
                index++;
            } else {
                isDone = true;
            }
        } while (!isDone);

        return slug;

    }

}
