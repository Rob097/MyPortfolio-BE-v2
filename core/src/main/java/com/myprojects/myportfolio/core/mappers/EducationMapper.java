package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.core.dao.Education;
import com.myprojects.myportfolio.core.dto.EducationDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {StoryMapper.class, SkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface EducationMapper extends BaseMapper<Education, EducationDto> {

    @Override
    @Mapping(target = "userId", source = "user.id")
    EducationDto mapToDto(Education entity);

    @Override
    @Mapping(target = "user.id", source = "userId")
    Education mapToDao(EducationDto dto);

}
