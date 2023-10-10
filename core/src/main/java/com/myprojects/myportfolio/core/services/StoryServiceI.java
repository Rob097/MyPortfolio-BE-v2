package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.NewStory;
import org.springframework.data.jpa.domain.Specification;

public interface StoryServiceI extends BaseServiceI<NewStory> {

    NewStory findBy(Specification<NewStory> specification);

}
