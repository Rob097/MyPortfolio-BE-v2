package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.dao.Education;
import com.myprojects.myportfolio.core.dto.EducationDto;
import com.myprojects.myportfolio.core.mappers.skills.SkillMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {StoryMapper.class, SkillMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class EducationMapper extends BaseMapper<Education, EducationDto> {

    @Override
    @Mapping(target = "userId", source = "entity.user.id")
    public abstract EducationDto mapToDto(Education entity, IView view);

    @Override
    @Mapping(target = "user.id", source = "userId")
    public abstract Education mapToDao(EducationDto dto);

}
