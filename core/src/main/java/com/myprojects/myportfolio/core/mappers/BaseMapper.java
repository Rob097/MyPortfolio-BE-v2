package com.myprojects.myportfolio.core.mappers;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.dao.WithStatusDao;
import com.myprojects.myportfolio.core.dao.enums.EntitiesStatusEnum;
import com.myprojects.myportfolio.core.dto.BaseDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMapper<A extends BaseDao, T extends BaseDto> {

    @Autowired
    private UtilsServiceI utilsService;

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

    @AfterMapping
    @SuppressWarnings("unchecked")
    protected T afterMappingToDto(@MappingTarget T dto, A entity, IView view) {
        try {
            if (entity instanceof WithStatusDao) {
                boolean isOfCurrentUser = false;
                try {
                    isOfCurrentUser = utilsService.isOfCurrentUser(entity, false);
                } catch (Exception e) {
                    // May happen an exception during delete operations.
                }

                // If the entity is NOT of the current user we have to return it only if its status is PUBLISHED. Otherwise, we return a new instance of the dto:
                if (!isOfCurrentUser && !((WithStatusDao) entity).getStatus().equals(EntitiesStatusEnum.PUBLISHED)) {
                    return (T) dto.getClass().getDeclaredConstructor().newInstance();
                }
            }
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error in afterMappingToDto method", e);
        }
    }

}
