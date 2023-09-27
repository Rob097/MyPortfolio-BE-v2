package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.newDataModel.dao.BaseDao;
import com.myprojects.myportfolio.core.newDataModel.dto.BaseDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.BaseMapper;
import com.myprojects.myportfolio.core.newDataModel.services.BaseServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
public abstract class BaseController<A extends BaseDao, T extends BaseDto> extends IController<T> {

    protected BaseServiceI<A> service;

    protected BaseMapper<A, T> mapper;

    @Override
    @GetMapping()
    public ResponseEntity<MessageResources<T>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view,
            Pageable pageable
    ) throws Exception {
        Specification<A> specifications = this.defineFilters(filters);

        Slice<A> users = service.findAll(specifications, pageable);

        return this.buildSuccessResponses(users.map(mapper::mapToDto), view);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<MessageResource<T>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(id, fieldMissing("id"));
        A user = service.findById(id);

        return this.buildSuccessResponse(mapper.mapToDto(user), view);
    }

    @Override
    @PostMapping()
    public ResponseEntity<MessageResource<T>> create(
            @Valid @RequestBody T user
    ) throws Exception {
        Validate.notNull(user, resourceMissing());

        A newUser = service.save(mapper.mapToDao(user));
        return this.buildSuccessResponse(mapper.mapToDto(newUser));
    }

    @Override
    @PutMapping(value = "/{id}")
    public ResponseEntity<MessageResource<T>> update(
            @PathVariable("id") Integer id,
            @Valid @RequestBody T user
    ) throws Exception {
        Validate.notNull(user, resourceMissing());
        Validate.notNull(user.getId(), fieldMissing("id"));
        Validate.isTrue(user.getId().equals(id), "The request's id and the body's id are different.");

        A updatedUser = service.update(mapper.mapToDao(user));
        return this.buildSuccessResponse(mapper.mapToDto(updatedUser));
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MessageResource<T>> delete(
            @PathVariable("id") Integer id
    ) throws Exception {
        Validate.notNull(id, fieldMissing("id"));

        A entityToDelete = service.findById(id);
        Validate.notNull(entityToDelete, noEntityFound(id));

        service.delete(entityToDelete);
        return this.buildSuccessResponse(mapper.mapToDto(entityToDelete));
    }
}
