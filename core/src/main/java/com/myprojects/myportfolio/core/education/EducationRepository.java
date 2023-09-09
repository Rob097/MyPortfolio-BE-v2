package com.myprojects.myportfolio.core.education;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer>, JpaSpecificationExecutor<Education> {

    @Query("SELECT e FROM Education e WHERE e.slug = ?1")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Optional<Education> findBySlug(String slug);

    @Query("SELECT slug from Education where user.id = ?1")
    List<String> findAllSlugs(Integer userId);

}
