package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import com.myprojects.myportfolio.core.newDataModel.dto.NewUserDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.UserMapper;
import com.myprojects.myportfolio.core.newDataModel.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(path = "/slug/{slug}")
    public ResponseEntity<MessageResource<NewUserDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        NewUser user = userService.findBy(findByEquals(NewUser.FIELDS.SLUG.name(), slug));

        return this.buildSuccessResponse(userMapper.mapToDto(user), view);
    }

    @GetMapping(path = "/slugs")
    public ResponseEntity<MessageResources<String>> get() throws Exception {
        List<String> slugs = userService.findAllSlugs();
        return this.buildSuccessResponsesOfGenericType(slugs, Normal.value, new ArrayList<>(), false);
    }

}
