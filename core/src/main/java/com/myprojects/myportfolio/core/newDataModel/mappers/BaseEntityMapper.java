package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.BaseDao;
import com.myprojects.myportfolio.core.newDataModel.dto.BaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface BaseEntityMapper extends BaseMapper<BaseDao, BaseDto> {
}
