package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Project;
import org.springframework.data.jpa.domain.Specification;

public interface ProjectServiceI extends BaseServiceI<Project> {

    Project findBy(Specification<Project> specification);

}
