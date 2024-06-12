package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.SetUpRequest;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.dto.UserDto;
import com.myprojects.myportfolio.core.mappers.UserMapper;
import com.myprojects.myportfolio.core.repositories.UserRepository;
import com.myprojects.myportfolio.core.services.AttachmentServiceI;
import com.myprojects.myportfolio.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController("UserController")
@RequestMapping("${core-module-basic-path}" + "/users")
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class UserController extends UserRelatedBaseController<User, UserDto> {

    private final UserService userService;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final AttachmentServiceI attachmentService;

    public UserController(UserRepository userRepository, UserService userService, UserMapper userMapper, AttachmentServiceI attachmentService) {
        super(userService, userMapper);

        this.userService = userService;
        this.userMapper = userMapper;

        this.userRepository = userRepository;
        this.attachmentService = attachmentService;
    }

    /** Methods, if not overridden under, are implemented in super class. */

    /***********************/
    /*** Override Methods **/
    /***********************/

    @GetMapping(path = "/slug/{slug}")
    public ResponseEntity<MessageResource<UserDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        User user = userService.findBy(findByEquals(User.FIELDS.SLUG.getName(), slug));

        return this.buildSuccessResponse(userMapper.mapToDto(user, view), view);
    }

    @GetMapping(path = "/slugs")
    public ResponseEntity<MessageResources<String>> get() throws Exception {
        List<String> slugs = userService.findAllSlugs();
        return this.buildSuccessResponsesOfGenericType(slugs, Normal.value, new ArrayList<>(), false);
    }

    @PutMapping(path = "/{id}/setup")
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.getName()) || @utilsService.hasId(#id)")
    public ResponseEntity<MessageResource<UserDto>> setUp(
            @PathVariable("id") Integer id,
            @RequestBody SetUpRequest request
    ) {
        Validate.notNull(request, "No valid operation was provided.");

        User updatedUser = userService.setUp(id, request);

        return this.buildSuccessResponse(userMapper.mapToDto(updatedUser));
    }

    @PutMapping(path = "/patch/{id}")
    @PreAuthorize("hasAnyRole(T(com.myprojects.myportfolio.clients.auth.ApplicationUserRole).SYS_ADMIN.getName()) || @utilsService.hasId(#id)")
    public ResponseEntity<MessageResource<UserDto>> patch(
            @PathVariable("id") Integer id,
            @RequestBody List<PatchOperation> operations
    ) {
        Validate.notEmpty(operations, "No valid operation was provided.");

        boolean isToUpdate = false;

        User user = userService.findById(id);

        for (PatchOperation operation : operations) {
            if (operation.getPath().matches("^/firstName") && operation.getOp() == PatchOperation.Op.replace) {
                String firstName = operation.getValue();
                user.setFirstName(firstName);
                isToUpdate = true;
            } else if (operation.getPath().matches("^/lastName") && operation.getOp() == PatchOperation.Op.replace) {
                String lastName = operation.getValue();
                user.setLastName(lastName);
                isToUpdate = true;
            } else if (operation.getPath().matches("^/email") && operation.getOp() == PatchOperation.Op.replace) {
                String email = operation.getValue();
                user.setEmail(email);
                isToUpdate = true;
            }
        }

        if (isToUpdate) {
            user = userService.update(user);
        }

        return this.buildSuccessResponse(userMapper.mapToDto(user, Normal.value));
    }

    @GetMapping(path = "/fromAuth/getNextId")
    public ResponseEntity<MessageResource<Integer>> getNextId() {
        Integer nextId = userRepository.getNextId();
        userRepository.incrementNextId();
        return this.buildSuccessResponseOfGenericType(nextId, Normal.value, new ArrayList<>(), false);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MessageResource<UserDto>> delete(
            @PathVariable("id") Integer id
    ) throws Exception {

        // Safety check
        if (!utilsService.isOfCurrentUser(User.builder().id(id).build(), false)) {
            throw new Exception("You can't delete this user because it's not you.");
        }

        // Delete all attachments of the user (from firebase and DB)
        attachmentService.deleteByUserId(id);

        // Delete the user and all related data
        return super.delete(id);
    }

}
