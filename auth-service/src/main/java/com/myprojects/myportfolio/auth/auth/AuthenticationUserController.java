package com.myprojects.myportfolio.auth.auth;

import com.myprojects.myportfolio.auth.dto.SignUPRequest;
import com.myprojects.myportfolio.auth.dto.mapper.DBUserMapper;
import com.myprojects.myportfolio.auth.dto.mapper.SignUPMapper;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.user.UserClient;
import com.myprojects.myportfolio.clients.user.UserR;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    private final UserClient userClient;

    @PostMapping(path="/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<UserR>> create(@RequestBody @Valid SignUPRequest user) {
        Validate.notNull(user, "Nessuna risorsa da creare passata.");

        DBUser userToRegister = signUPMapper.map(user);

        DBUser registeredUser = this.applicationUserService.registerUser(userToRegister);

        // Call the Core microservice and save also an application user
        UserR applicationUser = null;
        try {
            applicationUser = userClient.create(dbUserMapper.map(registeredUser)).getBody().getContent();
        } catch (Exception e) {
            log.error(e.getMessage());
            this.applicationUserService.deleteUser(registeredUser);
            throw new RuntimeException(e);
        }

        MessageResource<UserR> result = new MessageResource<>(applicationUser);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
