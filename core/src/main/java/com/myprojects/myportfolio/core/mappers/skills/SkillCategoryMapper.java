package com.myprojects.myportfolio.core.mappers.skills;

import com.myprojects.myportfolio.core.mappers.BaseMapper;
import com.myprojects.myportfolio.core.dao.skills.NewSkillCategory;
import com.myprojects.myportfolio.core.dto.skills.NewSkillCategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillCategoryMapper extends BaseMapper<NewSkillCategory, NewSkillCategoryDto> {
}
