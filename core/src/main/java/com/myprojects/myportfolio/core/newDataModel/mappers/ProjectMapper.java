package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import com.myprojects.myportfolio.core.newDataModel.dto.NewProjectDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.skills.SkillMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {StoryMapper.class, SkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ProjectMapper extends BaseMapper<NewProject, NewProjectDto> {

    @Override
    @Mapping(target = "userId", source = "user.id")
    NewProjectDto mapToDto(NewProject entity);

    @Override
    @Mapping(target = "user.id", source = "userId")
    NewProject mapToDao(NewProjectDto dto);

}
