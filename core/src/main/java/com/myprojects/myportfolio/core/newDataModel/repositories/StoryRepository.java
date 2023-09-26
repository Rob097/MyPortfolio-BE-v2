package com.myprojects.myportfolio.core.newDataModel.repositories;

import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "newStoryRepository")
public interface StoryRepository extends BaseRepository<NewStory, Integer> {

    @Override
    Optional<NewStory> findBySlug(String slug);

}
