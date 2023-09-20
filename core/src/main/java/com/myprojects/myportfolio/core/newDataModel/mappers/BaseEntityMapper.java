package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.BaseDao;
import com.myprojects.myportfolio.core.newDataModel.dto.BaseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaseEntityMapper extends BaseMapper<BaseDao, BaseDto> {
}
