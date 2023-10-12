package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Experience;
import org.springframework.data.jpa.domain.Specification;

public interface ExperienceServiceI extends BaseServiceI<Experience> {

    Experience findBy(Specification<Experience> specification);
}