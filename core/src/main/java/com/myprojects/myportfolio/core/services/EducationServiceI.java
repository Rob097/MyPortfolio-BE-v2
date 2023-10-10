package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.NewEducation;
import org.springframework.data.jpa.domain.Specification;

public interface EducationServiceI extends BaseServiceI<NewEducation> {

    NewEducation findBy(Specification<NewEducation> specification);
}
