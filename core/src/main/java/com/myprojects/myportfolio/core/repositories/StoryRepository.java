package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.Story;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "StoryRepository")
public interface StoryRepository extends BaseRepository<Story, Integer>, UserRelatedRepository<Story> {

    @Override
    Optional<Story> findBySlug(String slug);

    @Override
    @Query("SELECT 1 FROM Story s WHERE s.slug = :#{#slug} AND s.diary.id = :#{#story.diary.id}")
    Optional<Story> findBySlugConstraint(@Param("slug") String slug, @Param("story") Object story);

    @Override
    @Query("SELECT s.slug FROM Story s WHERE s.diary.user.id = :#{#userId} AND (:#{#isCurrentUser} = true OR s.status = 'PUBLISHED')")
    Optional<List<String>> findSlugsByUserId(@Param("userId") Integer userId, @Param("isCurrentUser") boolean isCurrentUser);

}
