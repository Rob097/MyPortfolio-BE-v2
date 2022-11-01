package com.myprojects.myportfolio.core.diary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    @Query("SELECT DISTINCT DIA.id FROM Diary DIA WHERE DIA.user.id = ?1")
    Optional<Integer> findIdByUserId(Integer userId);

    Optional<DiaryProjection> findByUserId(Integer userId);
}
