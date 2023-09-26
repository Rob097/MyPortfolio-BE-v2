package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.SlugDao;
import com.myprojects.myportfolio.core.newDataModel.dto.SlugDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface SlugEntityMapper extends BaseMapper<SlugDao, SlugDto> {
}
