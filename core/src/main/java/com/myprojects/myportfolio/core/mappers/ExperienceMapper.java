package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.core.dao.Experience;
import com.myprojects.myportfolio.core.dto.ExperienceDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {StoryMapper.class, SkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ExperienceMapper extends BaseMapper<Experience, ExperienceDto> {

    @Override
    @Mapping(target = "userId", source = "user.id")
    ExperienceDto mapToDto(Experience entity);

    @Override
    @Mapping(target = "user.id", source = "userId")
    Experience mapToDao(ExperienceDto dto);
}
