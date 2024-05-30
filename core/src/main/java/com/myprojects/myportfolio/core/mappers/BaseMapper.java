package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.dao.Story;
import com.myprojects.myportfolio.core.dto.BaseDto;
import com.myprojects.myportfolio.core.dto.StoryDto;
import org.mapstruct.BeforeMapping;

public abstract class BaseMapper<A extends BaseDao, T extends BaseDto> {


    public abstract T mapToDto(A dao, IView view);

    public abstract A mapToDao(T dto);

    public T mapToDto(A entity) {
        return mapToDto(entity, Verbose.value);
    }

    @BeforeMapping
    protected void beforeMapping(A dao, IView view) {
        if (!(view instanceof Verbose)) {
            dao.clearRelationships();
        }
    }

}
