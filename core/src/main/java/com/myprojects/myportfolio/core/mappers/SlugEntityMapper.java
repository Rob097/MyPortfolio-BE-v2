package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.core.dto.SlugDto;
import com.myprojects.myportfolio.core.dao.SlugDao;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface SlugEntityMapper extends BaseMapper<SlugDao, SlugDto> {
}
