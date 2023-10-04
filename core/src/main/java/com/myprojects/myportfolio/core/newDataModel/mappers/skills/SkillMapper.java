package com.myprojects.myportfolio.core.newDataModel.mappers.skills;

import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewSkill;
import com.myprojects.myportfolio.core.newDataModel.dto.skills.NewSkillDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = {SkillCategoryMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface SkillMapper extends BaseMapper<NewSkill, NewSkillDto> {
}
