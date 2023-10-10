package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.core.dao.NewEducation;
import com.myprojects.myportfolio.core.dto.NewEducationDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {StoryMapper.class, SkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface EducationMapper extends BaseMapper<NewEducation, NewEducationDto> {

    @Override
    @Mapping(target = "userId", source = "user.id")
    NewEducationDto mapToDto(NewEducation entity);

    @Override
    @Mapping(target = "user.id", source = "userId")
    NewEducation mapToDao(NewEducationDto dto);

}
