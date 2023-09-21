package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import com.myprojects.myportfolio.core.newDataModel.dto.NewProjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { StoryMapper.class })
public interface ProjectMapper extends BaseMapper<NewProject, NewProjectDto> {
}
