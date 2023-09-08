package com.myprojects.myportfolio.core.education;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer>, JpaSpecificationExecutor<Education> {

    Optional<Education> findBySlug(String slug);

    @Query("SELECT slug from Education where user.id = ?1")
    List<String> findAllSlugs(Integer userId);

}
