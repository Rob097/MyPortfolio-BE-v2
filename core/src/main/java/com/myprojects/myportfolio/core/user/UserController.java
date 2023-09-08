package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.user.mappers.UserMapper;
import com.myprojects.myportfolio.core.user.mappers.UserRMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${core-module-basic-path}" + "/users")
public class UserController implements IController<UserR> {

    private final UserServiceI userService;

    private final UserRMapper userRMapper;

    private final UserMapper userMapper;

    private final HttpServletRequest httpServletRequest;

    private final UserRepository userRepository;

    public UserController(UserServiceI userService, UserRMapper userRMapper, UserMapper userMapper, HttpServletRequest httpServletRequest, UserRepository userRepository) {
        this.userService = userService;
        this.userRMapper = userRMapper;
        this.userMapper = userMapper;
        this.httpServletRequest = httpServletRequest;
        this.userRepository = userRepository;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResources<UserR>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view,
            Pageable pageable
    ) throws Exception {
        Specification<User> specifications = this.defineFiltersAndStoreView(filters, view, this.httpServletRequest);

        Slice<User> users = userService.findAll(specifications, pageable);

        return this.buildSuccessResponses(users.map(userRMapper::map));
    }

    @Override
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<UserR>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(id, "Mandatory parameter is missing: id.");
        this.storeRequestView(view, httpServletRequest);

        User user = userService.findById(id);

        return this.buildSuccessResponse(userRMapper.map(user));
    }

    @GetMapping(path="/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<UserR>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(slug, "Mandatory parameter is missing: slug.");
        this.storeRequestView(view, httpServletRequest);

        User user = userService.findBySlug(slug);

        return this.buildSuccessResponse(userRMapper.map(user));
    }

    @GetMapping(path="/slugs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResources<String>> getAllSlugs() throws Exception {

        List<String> slugs = userRepository.findAllSlugs();
        return this.buildSuccessResponses(slugs);
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<UserR>> create(@RequestBody UserR user) {
        Validate.notNull(user, "No valid resource was provided..");

        User newUser = userService.save(userMapper.map(user));
        return this.buildSuccessResponse(userRMapper.map(newUser));
    }

    @Override
    @PutMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole(T(ApplicationUserRole).ADMIN.getName()) || @userService.hasId(#id)")
    public ResponseEntity<MessageResource<UserR>> update(
            @PathVariable("id") Integer id,
            @RequestBody UserR user
    ) {
        Validate.notNull(user, "No valid resource to update was provided.");
        Validate.notNull(user.getId(), "Mandatory parameter is missing: id user.");
        Validate.isTrue(user.getId().equals(id), "The request's id and the body's id are different.");

        User userToUpdate = userService.findById(user.getId());
        User updatedUser = userService.update(userMapper.map(user, userToUpdate));
        return this.buildSuccessResponse(userRMapper.map(updatedUser));
    }

    @Override
    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@userService.hasId(#id)")
    public ResponseEntity<MessageResource<UserR>> delete(@PathVariable("id") Integer id) {
        Validate.notNull(id, "No valid parameters were provided.");

        User userToDelete = userService.findById(id);
        Validate.notNull(userToDelete, "No valid user found with id " + id);

        userService.delete(userToDelete);
        return this.buildSuccessResponse(userRMapper.map(userToDelete));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority(T(ApplicationUserPermission).USERS_WRITE.getName()) && @userService.hasId(#id)")
    public ResponseEntity<MessageResource<UserR>> patch(
            @PathVariable("id") Integer id,
            @RequestBody List<PatchOperation> operations
    ) throws Exception {
        Validate.notEmpty(operations, "No valid operation was provided.");

        boolean isToUpdate = false;

        User user = userService.findById(id);

        for (PatchOperation operation : operations) {
            if (operation.getPath().matches("^/firstName") && operation.getOp() == PatchOperation.Op.replace) {
                String firstName = operation.getValue();
                user.setFirstName(firstName);
                isToUpdate = true;
            } else if (operation.getPath().matches("^/email") && operation.getOp() == PatchOperation.Op.replace) {
                String email = operation.getValue();
                user.setEmail(email);
                isToUpdate = true;
            }
        }

        if(isToUpdate) {
            user = userService.update(user);
        }
        UserR userR = userRMapper.map(user);

        return new ResponseEntity<>(new MessageResource<>(userR), HttpStatus.OK);
    }

}
