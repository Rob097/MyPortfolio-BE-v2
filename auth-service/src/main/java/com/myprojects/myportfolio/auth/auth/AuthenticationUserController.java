package com.myprojects.myportfolio.auth.auth;

import com.myprojects.myportfolio.auth.dto.SignUPRequest;
import com.myprojects.myportfolio.auth.dto.mapper.DBUserMapper;
import com.myprojects.myportfolio.auth.dto.mapper.SignUPMapper;
import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.user.UserR;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/auth")
@Slf4j
@AllArgsConstructor
public class AuthenticationUserController {

    @Autowired
    private AuthenticationUserService applicationUserService;

    @Autowired
    private SignUPMapper signUPMapper;

    @Autowired
    private DBUserMapper dbUserMapper;

    private final PasswordEncoder passwordEncoder;

    @PostMapping(path="/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<UserR>> create(@RequestBody @Valid SignUPRequest user) {
        Validate.notNull(user, "No valid resource was provided.");
        List<Message> messages = new ArrayList<>();

        DBUser userToRegister = signUPMapper.map(user);

        DBUser registeredUser = this.applicationUserService.registerUser(userToRegister);
        messages.add(new Message("User successfully registered."));

        MessageResource<UserR> result = new MessageResource<>(dbUserMapper.map(registeredUser), messages);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@applicationUserService.hasId(#id)")
    public ResponseEntity<MessageResource<UserR>> patch(@PathVariable("id") Integer id, @RequestBody List<PatchOperation> operations) throws Exception {
        Validate.notEmpty(operations, "No valid operation was provided.");

        List<Message> messages = new ArrayList<>();
        boolean isToUpdate = false;
        boolean isToUpdateCoreUser = false;

        DBUser user = applicationUserService.loadUserById(id);

        for (PatchOperation operation : operations) {
            if (operation.getPath().matches("^/password") && operation.getOp() == PatchOperation.Op.replace) {
                String[] passwords = operation.getValue()!=null ? operation.getValue().split("$_$") : null;

                if(passwords!=null && passwords.length == 2){
                    String oldPassword = passwords[0];
                    String newPassword = passwords[1];

                    if(!passwordEncoder.encode(oldPassword).equals(user.getPassword())){
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


        if(isToUpdate) {
            user = applicationUserService.updateUser(user, isToUpdateCoreUser ? operations : null);
            messages.add(new Message("Update successful."));
        } else {
            messages.add(new Message("No updates made."));
        }

        MessageResource<UserR> result = new MessageResource<>(dbUserMapper.map(user), messages);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@applicationUserService.hasId(#id)")
    public ResponseEntity<MessageResource<UserR>> delete(@PathVariable("id") Integer id) {
        Validate.notNull(id, "No valid parameters were provided.");
        List<Message> messages = new ArrayList<>();

        DBUser user = applicationUserService.loadUserById(id);
        Validate.notNull(user, "No valid user found with id " + id);

        this.applicationUserService.deleteUser(user);
        messages.add(new Message("User successfully deleted."));

        MessageResource<UserR> result = new MessageResource<>(dbUserMapper.map(user), messages);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
