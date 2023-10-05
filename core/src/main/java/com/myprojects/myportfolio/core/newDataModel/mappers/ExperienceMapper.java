package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.NewExperience;
import com.myprojects.myportfolio.core.newDataModel.dto.NewExperienceDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.skills.SkillMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {StoryMapper.class, SkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ExperienceMapper extends BaseMapper<NewExperience, NewExperienceDto> {

    @Override
    @Mapping(target = "userId", source = "user.id")
    NewExperienceDto mapToDto(NewExperience entity);

    @Override
    @Mapping(target = "user.id", source = "userId")
    NewExperience mapToDao(NewExperienceDto dto);
}
