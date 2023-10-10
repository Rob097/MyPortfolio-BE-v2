package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Education;
import org.springframework.data.jpa.domain.Specification;

public interface EducationServiceI extends BaseServiceI<Education> {

    Education findBy(Specification<Education> specification);
}
