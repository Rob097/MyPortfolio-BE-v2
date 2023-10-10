package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.Diary;
import org.springframework.stereotype.Repository;

@Repository(value = "DiaryRepository")
public interface DiaryRepository extends BaseRepository<Diary, Integer> {
}
