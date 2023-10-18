package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.dao.Project;
import com.myprojects.myportfolio.core.dto.ProjectDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {StoryMapper.class, SkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ProjectMapper extends BaseMapper<Project, ProjectDto> {

    @Override
    @Mapping(target = "userId", source = "entity.user.id")
    ProjectDto mapToDto(Project entity, IView view);

    @Override
    @Mapping(target = "user.id", source = "userId")
    Project mapToDao(ProjectDto dto);

}
