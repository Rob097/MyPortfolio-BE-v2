package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import org.springframework.data.jpa.domain.Specification;

public interface ProjectServiceI extends BaseServiceI<NewProject> {

    NewProject findBy(Specification<NewProject> specification);

}
