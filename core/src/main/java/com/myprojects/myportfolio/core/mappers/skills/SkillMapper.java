package com.myprojects.myportfolio.core.mappers.skills;

import com.myprojects.myportfolio.core.mappers.BaseMapper;
import com.myprojects.myportfolio.core.dao.skills.NewSkill;
import com.myprojects.myportfolio.core.dto.skills.NewSkillDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {SkillCategoryMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface SkillMapper extends BaseMapper<NewSkill, NewSkillDto> {
}
