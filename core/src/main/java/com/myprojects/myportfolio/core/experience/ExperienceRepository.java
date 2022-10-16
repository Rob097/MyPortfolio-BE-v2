package com.myprojects.myportfolio.core.experience;

import com.myprojects.myportfolio.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

    @Query("SELECT experience FROM Experience experience WHERE experience.user = ?1")
    List<Experience> findAllByUser(User user);

}
