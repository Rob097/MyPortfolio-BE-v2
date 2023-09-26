package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import org.springframework.data.jpa.domain.Specification;

public interface StoryServiceI extends BaseServiceI<NewStory> {

    NewStory findBy(Specification<NewStory> specification);

}
