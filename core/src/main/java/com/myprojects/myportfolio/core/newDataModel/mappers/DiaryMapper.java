package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;
import com.myprojects.myportfolio.core.newDataModel.dto.NewDiaryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiaryMapper extends BaseMapper<NewDiary, NewDiaryDto> {
}
