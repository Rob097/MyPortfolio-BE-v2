package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.dao.Experience;
import com.myprojects.myportfolio.core.dto.ExperienceDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {StoryMapper.class, SkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class ExperienceMapper extends BaseMapper<Experience, ExperienceDto> {

    @Override
    @Mapping(target = "userId", source = "entity.user.id")
    public abstract ExperienceDto mapToDto(Experience entity, IView view);

    @Override
    @Mapping(target = "user.id", source = "userId")
    public abstract Experience mapToDao(ExperienceDto dto);
}
