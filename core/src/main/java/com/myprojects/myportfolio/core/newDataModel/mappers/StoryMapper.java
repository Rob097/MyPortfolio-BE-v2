package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.dto.NewStoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryMapper extends BaseMapper<NewStory, NewStoryDto> {

    @Override
    @Mapping( target = "diaryId", source = "diary.id" )
    NewStoryDto mapToDto(NewStory entity);

    @Override
    @Mapping( target = "diary.id", source = "diaryId" )
    NewStory mapToDao(NewStoryDto dto);

}
