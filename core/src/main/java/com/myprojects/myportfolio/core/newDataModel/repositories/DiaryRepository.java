package com.myprojects.myportfolio.core.newDataModel.repositories;

import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;
import org.springframework.stereotype.Repository;

@Repository(value="newDiaryRepository")
public interface DiaryRepository extends BaseRepository<NewDiary, Integer> {
}
