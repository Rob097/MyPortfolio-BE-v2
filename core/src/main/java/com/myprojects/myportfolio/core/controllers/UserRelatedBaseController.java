package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.dto.BaseDto;
import com.myprojects.myportfolio.core.dto.groups.OnCreate;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.mappers.BaseMapper;
import com.myprojects.myportfolio.core.services.BaseServiceI;
import com.myprojects.myportfolio.core.services.StoryServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
public abstract class UserRelatedBaseController<A extends BaseDao, T extends BaseDto> extends BaseController<A, T> {

    private final BaseServiceI<A> service;

    private final BaseMapper<A, T> mapper;

    public UserRelatedBaseController(BaseServiceI<A> service, BaseMapper<A, T> mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }

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

        // If user is logged in, get only the projects of the logged-in user
        User currentLoggedInUser = utilsService.getCurrentLoggedInUser();
        if (currentLoggedInUser != null && (filters == null || !filters.contains("user.id"))) {
            String key = service instanceof StoryServiceI ? "diary.user.id" : "user.id";
            log.info("Adding user filter: {}: {}", key, currentLoggedInUser.getId());

            Specification<A> userFilter = findByEquals(key, currentLoggedInUser.getId());
            if (specifications != null) {
                specifications = specifications.and(userFilter);
            } else {
                specifications = userFilter;
            }
        }

        Page<A> entities = service.findAll(specifications, pageable);

        return this.buildSuccessResponses(entities.map((entity) -> mapper.mapToDto(entity, view)), view);
    }

    /**
     * Here are overridden all the methods that needs a specific "Pre Authorize" condition for all the entities that are related to the current user.
     * Sadly, Spring security doesn't allow to override the @PreAuthorize annotation, so we have to create this common class to not set these conditions in every controller.
     * Entities not related to the current user have to extend the BaseController class and if they need a specific "Pre Authorize" condition, they have to override the method.
     */

    @Override
    @PostMapping()
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.name() ) || @utilsService.isOfCurrentUser(#entity, true)")
    public ResponseEntity<MessageResource<T>> create(
            @Validated(OnCreate.class) @RequestBody T entity
    ) throws Exception {
        return super.create(entity);
    }

    @Override
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.getName()) || @utilsService.isOfCurrentUser(#entity, false)")
    public ResponseEntity<MessageResource<T>> update(
            @PathVariable("id") Integer id,
            @Validated(OnUpdate.class) @RequestBody T entity
    ) throws Exception {
        return super.update(id, entity);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MessageResource<T>> delete(
            @PathVariable("id") Integer id
    ) throws Exception {
        Validate.notNull(id, fieldMissing("id"));

        A entityToDelete = service.findById(id);
        Validate.notNull(entityToDelete, noEntityFound(id));

        if (!utilsService.isOfCurrentUser(entityToDelete, false)) {
            throw new Exception("You can't delete it because is not yours.");
        }

        return super.delete(id);
    }

}
