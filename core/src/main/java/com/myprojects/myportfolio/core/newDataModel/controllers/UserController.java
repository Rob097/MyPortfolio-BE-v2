package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import com.myprojects.myportfolio.core.newDataModel.dto.NewUserDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.UserMapper;
import com.myprojects.myportfolio.core.newDataModel.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("newUserController")
@RequestMapping("${core-module-basic-path}" + "/new/users")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class UserController extends BaseController<NewUser, NewUserDto> {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.service = userService;
        this.mapper = userMapper;

        this.userService = userService;
        this.userMapper = userMapper;
    }

    /** Methods, if not overridden above, are implemented in super class. */

    /***********************/
    /*** Override Methods **/
    /***********************/

    @GetMapping(path = "/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<NewUserDto>> get(
            @PathVariable("slug") String slug
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        NewUser user = userService.findBy(findByEquals(NewUser.FIELDS.SLUG.name(), slug));

        return this.buildSuccessResponse(userMapper.mapToDto(user));
    }

    @Override
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole(T(ApplicationUserRole).ADMIN.getName()) || @newUserService.hasId(#id)")
    public ResponseEntity<MessageResource<NewUserDto>> update(
            @PathVariable("id") Integer id,
            @RequestBody NewUserDto user
    ) throws Exception {
        return super.update(id, user);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@userService.hasId(#id)")
    public ResponseEntity<MessageResource<NewUserDto>> delete(
            @PathVariable("id") Integer id
    ) throws Exception {
        return super.delete(id);
    }
}
