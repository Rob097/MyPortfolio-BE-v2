package com.myprojects.myportfolio.core.mappers.skills;

import com.myprojects.myportfolio.core.dao.skills.SkillCategory;
import com.myprojects.myportfolio.core.dto.skills.SkillCategoryDto;
import com.myprojects.myportfolio.core.mappers.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillCategoryMapper extends BaseMapper<SkillCategory, SkillCategoryDto> {
}
