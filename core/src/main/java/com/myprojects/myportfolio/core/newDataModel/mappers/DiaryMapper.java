package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;
import com.myprojects.myportfolio.core.newDataModel.dto.NewDiaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {StoryMapper.class, UserMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface DiaryMapper extends BaseMapper<NewDiary, NewDiaryDto> {

    @Override
    @Mapping(target = "userId", source = "user.id")
    NewDiaryDto mapToDto(NewDiary entity);

    @Override
    @Mapping(target = "user.id", source = "userId")
    NewDiary mapToDao(NewDiaryDto dto);

}
