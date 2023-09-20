package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.dto.NewStoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoryMapper extends BaseMapper<NewStory, NewStoryDto> {
}
