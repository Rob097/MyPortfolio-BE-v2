package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.dto.BaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public abstract class BaseEntityMapper extends BaseMapper<BaseDao, BaseDto> {
}
