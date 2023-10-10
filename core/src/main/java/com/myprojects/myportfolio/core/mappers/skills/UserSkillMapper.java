package com.myprojects.myportfolio.core.mappers.skills;

import com.myprojects.myportfolio.core.dao.skills.NewUserSkill;
import com.myprojects.myportfolio.core.dto.skills.NewUserSkillDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SkillMapper.class})
public interface UserSkillMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "skillId", source = "skill.id")
    NewUserSkill mapToDao(NewUserSkillDto dto);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "skill.id", source = "skillId")
    NewUserSkillDto mapToDto(NewUserSkill dao);
}
