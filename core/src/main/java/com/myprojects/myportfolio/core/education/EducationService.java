package com.myprojects.myportfolio.core.education;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class EducationService {

    @Autowired
    private EducationRepository educationRepository;

    public Education findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Education> education = this.educationRepository.findById(id);
        return education.orElseThrow(() -> new NoSuchElementException("Impossible to found any education with id: " + id));
    }

}
