package com.myprojects.myportfolio.auth.controller;

import com.myprojects.myportfolio.auth.dao.DBUser;
import com.myprojects.myportfolio.auth.dto.CoreUser;
import com.myprojects.myportfolio.auth.dto.SignINRequest;
import com.myprojects.myportfolio.auth.dto.SignINResponse;
import com.myprojects.myportfolio.auth.dto.SignUPRequest;
import com.myprojects.myportfolio.auth.mapper.CoreUserMapper;
import com.myprojects.myportfolio.auth.mapper.SignUPMapper;
import com.myprojects.myportfolio.auth.service.AuthenticationUserServiceI;
import com.myprojects.myportfolio.auth.service.JwtServiceI;
import com.myprojects.myportfolio.clients.auth.AuthenticatedUserClaims;
import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.SetUpRequest;
import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${auth-module-basic-path}")
public class AuthenticationUserController {

    private final AuthenticationUserServiceI applicationUserService;

    private final SignUPMapper signUPMapper;

    private final CoreUserMapper coreUserMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtServiceI jwtService;

    @Autowired
    public AuthenticationUserController(AuthenticationUserServiceI applicationUserService, SignUPMapper signUPMapper, CoreUserMapper coreUserMapper, PasswordEncoder passwordEncoder, JwtServiceI jwtService) {
        this.applicationUserService = applicationUserService;
        this.signUPMapper = signUPMapper;
        this.coreUserMapper = coreUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam(name = "token") String token) {
        jwtService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/authorities")
    public ResponseEntity<AuthenticatedUserClaims> getAuthorities(@RequestParam(name = "token") String token) {
        AuthenticatedUserClaims authenticatedUserClaims = jwtService.getAuthorities(token);
        return ResponseEntity.ok(authenticatedUserClaims);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignINResponse> authenticateUser(@Valid @RequestBody SignINRequest loginRequest) {
        String token = applicationUserService.authenticateUser(loginRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return ResponseEntity.ok().headers(headers).body(new SignINResponse(token));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<SignINResponse> create(@RequestBody @Valid SignUPRequest user) {
        Validate.notNull(user, "No valid resource was provided.");

        DBUser userToRegister = signUPMapper.map(user);
        String token = this.applicationUserService.registerUser(userToRegister);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return ResponseEntity.ok().headers(headers).body(new SignINResponse(token));
    }

    @PutMapping(path = "/{id}/setup")
    @PreAuthorize("@applicationUserService.hasId(#id)")
    public ResponseEntity<MessageResource<?>> setUp(@PathVariable("id") Integer id, @RequestBody SetUpRequest request) throws Exception {
        Validate.notNull(request, "No valid request was provided.");
        List<Message> messages = new ArrayList<>();

        DBUser updatedUser = this.applicationUserService.setUpUser(id, request);
        messages.add(new Message("User setup completed successfully."));

        return new ResponseEntity<>(new MessageResource<>(coreUserMapper.map(updatedUser), messages), HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    @PreAuthorize("@applicationUserService.hasId(#id)")
    public ResponseEntity<MessageResource<CoreUser>> patch(@PathVariable("id") Integer id, @RequestBody List<PatchOperation> operations) throws Exception {
        Validate.notEmpty(operations, "No valid operation was provided.");

        List<Message> messages = new ArrayList<>();
        boolean isToUpdate = false;
        boolean isToUpdateCoreUser = false;

        DBUser user = applicationUserService.loadUserById(id);

        for (PatchOperation operation : operations) {
            if (operation.getPath().matches("^/password") && operation.getOp() == PatchOperation.Op.replace) {
                String[] passwords = operation.getValue() != null ? operation.getValue().split("$_$") : null;

                if (passwords != null && passwords.length == 2) {
                    String oldPassword = passwords[0];
                    String newPassword = passwords[1];

                    if (!passwordEncoder.encode(oldPassword).equals(user.getPassword())) {
                        throw new RuntimeException("The old password is not correct.");
                    }

                    user.setPassword(passwordEncoder.encode(newPassword));
                    isToUpdate = true;
                }

            }
            if (operation.getPath().matches("^/firstName") && operation.getOp() == PatchOperation.Op.replace) {
                String firstName = operation.getValue();
                user.setFirstName(firstName);
                isToUpdate = true;
                isToUpdateCoreUser = true;
            }
            if (operation.getPath().matches("^/lastName") && operation.getOp() == PatchOperation.Op.replace) {
                String lastName = operation.getValue();
                user.setLastName(lastName);
                isToUpdate = true;
                isToUpdateCoreUser = true;
            }
        }


        if (isToUpdate) {
            user = applicationUserService.updateUser(user, isToUpdateCoreUser ? operations : null);
            messages.add(new Message("Update successful."));
        } else {
            messages.add(new Message("No updates made."));
        }

        MessageResource<CoreUser> result = new MessageResource<>(coreUserMapper.map(user), messages);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("@applicationUserService.hasId(#id)")
    public ResponseEntity<MessageResource<CoreUser>> delete(@PathVariable("id") Integer id) {
        Validate.notNull(id, "No valid parameters were provided.");
        List<Message> messages = new ArrayList<>();

        DBUser user = applicationUserService.loadUserById(id);
        Validate.notNull(user, "No valid user found with id " + id);

        this.applicationUserService.deleteUser(user);
        messages.add(new Message("User successfully deleted."));

        MessageResource<CoreUser> result = new MessageResource<>(coreUserMapper.map(user), messages);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
