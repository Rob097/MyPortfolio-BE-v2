package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.dto.BaseDto;

public interface BaseMapper<A extends BaseDao, T extends BaseDto> {

    T mapToDto(A dao);

    A mapToDao(T dto);

}
