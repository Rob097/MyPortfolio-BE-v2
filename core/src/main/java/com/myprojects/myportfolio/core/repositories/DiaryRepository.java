package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.NewDiary;
import org.springframework.stereotype.Repository;

@Repository(value="newDiaryRepository")
public interface DiaryRepository extends BaseRepository<NewDiary, Integer> {
}
