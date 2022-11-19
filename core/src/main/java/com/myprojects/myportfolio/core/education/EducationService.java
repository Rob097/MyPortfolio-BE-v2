package com.myprojects.myportfolio.core.education;

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
public class EducationService {

    @Autowired
    private EducationRepository educationRepository;

    public Slice<Education> findAll(Specification specification, Pageable pageable){

        Slice<Education> educations = educationRepository.findAll(specification, pageable);

        return educations;
    }

    public Education findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Education> education = educationRepository.findById(id);
        return education.orElseThrow(() -> new NoSuchElementException("Impossible to found any education with id: " + id));
    }

    public Education save(Education education){
        Validate.notNull(education, "Mandatory parameter is missing: education.");

        if(education.getId()!=null) {
            Optional<Education> actual = educationRepository.findById(education.getId());
            Validate.isTrue(!actual.isPresent(), "It already exists an education with id: " + education.getId());
        }

        Education createdEducation = educationRepository.save(education);
        return createdEducation;
    }

    public Education update(Education educationToUpdate){
        Validate.notNull(educationToUpdate, "Mandatory parameter is missing: education.");
        Validate.notNull(educationToUpdate.getId(), "Mandatory parameter is missing: id education.");

        return educationRepository.save(educationToUpdate);
    }

    public void delete(Education educationToDelete){
        Validate.notNull(educationToDelete, "Mandatory parameter is missing: education.");

        this.educationRepository.delete(educationToDelete);
    }

}
