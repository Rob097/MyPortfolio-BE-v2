package com.myprojects.myportfolio.core.newDataModel.mappers;

import com.myprojects.myportfolio.core.newDataModel.dao.BaseDao;
import com.myprojects.myportfolio.core.newDataModel.dto.BaseDto;

public interface BaseMapper<A extends BaseDao, T extends BaseDto> {

    T mapToDto(A dao);

    A mapToDao(T dto);

}
