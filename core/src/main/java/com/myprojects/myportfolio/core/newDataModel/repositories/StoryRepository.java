package com.myprojects.myportfolio.core.newDataModel.repositories;

import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "newStoryRepository")
public interface StoryRepository extends BaseRepository<NewStory, Integer> {

    @Override
    Optional<NewStory> findBySlug(String slug);

    @Override
    @Query("SELECT 1 FROM NewStory s WHERE s.slug = :#{#slug} AND s.diary.id = :#{#story.diary.id}")
    Optional<NewStory> findBySlugConstraint(@Param("slug") String slug, @Param("story") Object story);

}
