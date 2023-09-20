package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.AuditableDao;
import com.myprojects.myportfolio.core.newDataModel.dto.AuditableDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditableEntityMapper extends BaseMapper<AuditableDao, AuditableDto> {
}
