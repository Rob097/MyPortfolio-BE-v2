package com.myprojects.myportfolio.core.mappers.skills;

import com.myprojects.myportfolio.core.dao.skills.UserSkill;
import com.myprojects.myportfolio.core.dto.skills.UserSkillDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SkillMapper.class})
public interface UserSkillMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "skillId", source = "skill.id")
    UserSkill mapToDao(UserSkillDto dto);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "skill.id", source = "skillId")
    UserSkillDto mapToDto(UserSkill dao);
}
