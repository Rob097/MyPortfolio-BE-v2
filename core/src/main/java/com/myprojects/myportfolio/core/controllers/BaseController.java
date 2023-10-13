package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.dto.BaseDto;
import com.myprojects.myportfolio.core.dto.groups.OnCreate;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.mappers.BaseMapper;
import com.myprojects.myportfolio.core.services.BaseServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
public abstract class BaseController<A extends BaseDao, T extends BaseDto> extends IController<T> {

    protected BaseServiceI<A> service;

    protected BaseMapper<A, T> mapper;

    @Autowired
    protected UtilsServiceI utilsService;

    @Override
    @GetMapping()
    public ResponseEntity<MessageResources<T>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view,
            Pageable pageable
    ) {
        Specification<A> specifications = this.defineFilters(filters);

        Slice<A> entities = service.findAll(specifications, pageable);

        return this.buildSuccessResponses(entities.map(mapper::mapToDto), view);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<MessageResource<T>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(id, fieldMissing("id"));
        A entity = service.findById(id);

        return this.buildSuccessResponse(mapper.mapToDto(entity), view);
    }

    @Override
    @PostMapping()
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.name() ) || @utilsService.isOfCurrentUser(#entity, true)")
    public ResponseEntity<MessageResource<T>> create(
            @Validated(OnCreate.class) @RequestBody T entity
    ) throws Exception {
        Validate.notNull(entity, resourceMissing());

        A newEntity = service.save(mapper.mapToDao(entity));
        return this.buildSuccessResponse(mapper.mapToDto(newEntity));
    }

    @Override
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.getName()) || @utilsService.isOfCurrentUser(#entity, false)")
    public ResponseEntity<MessageResource<T>> update(
            @PathVariable("id") Integer id,
            @Validated(OnUpdate.class) @RequestBody T entity
    ) throws Exception {
        Validate.notNull(entity, resourceMissing());
        Validate.notNull(entity.getId(), fieldMissing("id"));
        Validate.isTrue(entity.getId().equals(id), "The request's id and the body's id are different.");

        A updatedEntity = service.update(mapper.mapToDao(entity));
        return this.buildSuccessResponse(mapper.mapToDto(updatedEntity));
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MessageResource<T>> delete(
            @PathVariable("id") Integer id
    ) throws Exception {
        Validate.notNull(id, fieldMissing("id"));

        A entityToDelete = service.findById(id);
        Validate.notNull(entityToDelete, noEntityFound(id));

        if (!utilsService.isOfCurrentUser(mapper.mapToDto(entityToDelete), false)) {
            throw new Exception("You can't delete it because is not yours.");
        }

        service.delete(entityToDelete);
        return this.buildSuccessResponse(mapper.mapToDto(entityToDelete));
    }
}
