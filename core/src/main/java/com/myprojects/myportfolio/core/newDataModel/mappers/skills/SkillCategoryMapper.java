package com.myprojects.myportfolio.core.newDataModel.mappers.skills;

import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewSkillCategory;
import com.myprojects.myportfolio.core.newDataModel.dto.skills.NewSkillCategoryDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillCategoryMapper extends BaseMapper<NewSkillCategory, NewSkillCategoryDto> {
}
