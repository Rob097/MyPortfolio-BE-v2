package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.dto.BaseDto;
import org.mapstruct.BeforeMapping;

public interface BaseMapper<A extends BaseDao, T extends BaseDto> {

    T mapToDto(A dao, IView view);

    A mapToDao(T dto);

    @BeforeMapping
    default void beforeMapping(A dao, IView view) {
        if (!(view instanceof Verbose)) {
            dao.clearRelationships();
        }
    }

}
