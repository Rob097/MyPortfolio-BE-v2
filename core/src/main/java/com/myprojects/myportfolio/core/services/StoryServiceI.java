package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Story;
import org.springframework.data.jpa.domain.Specification;

public interface StoryServiceI extends BaseServiceI<Story> {

    Story findBy(Specification<Story> specification);

}
