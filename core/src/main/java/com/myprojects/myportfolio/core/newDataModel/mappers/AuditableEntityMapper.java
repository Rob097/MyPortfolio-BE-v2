package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.AuditableDao;
import com.myprojects.myportfolio.core.newDataModel.dto.AuditableDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface AuditableEntityMapper extends BaseMapper<AuditableDao, AuditableDto> {
}
