package com.myprojects.myportfolio.core.story;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer>, JpaSpecificationExecutor<Story> {

    @Query("SELECT s FROM Story s WHERE s.slug = ?1")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Optional<Story> findBySlug(String slug);

    @Query("SELECT slug from Story where diary.user.id = ?1")
    List<String> findAllSlugs(Integer userId);

}
