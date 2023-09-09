package com.myprojects.myportfolio.core.experience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Integer>, JpaSpecificationExecutor<Experience> {

    @Query("SELECT e FROM Experience e WHERE e.slug = ?1")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Optional<Experience> findBySlug(String slug);

    @Query("SELECT slug from Experience where user.id = ?1")
    List<String> findAllSlugs(Integer userId);

}
