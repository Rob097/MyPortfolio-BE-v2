package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.newDataModel.dao.NewExperience;
import org.springframework.data.jpa.domain.Specification;

public interface ExperienceServiceI extends BaseServiceI<NewExperience> {

    NewExperience findBy(Specification<NewExperience> specification);
}
