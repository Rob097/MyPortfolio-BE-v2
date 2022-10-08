package com.myprojects.myportfolio.core.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.user.UserQ;
import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.user.mappers.UserMapper;
import com.myprojects.myportfolio.core.user.mappers.UserRMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/core/users")
@Slf4j
public class UserController implements IController<UserR, UserQ> {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRMapper userRMapper;

    @Autowired
    private UserMapper userMapper;

    private ObjectMapper jacksonObjectMapper = new ObjectMapper();

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority(T(ApplicationUserPermission).USERS_READ.getName())")
    public ResponseEntity<MessageResources<UserR>> find(UserQ parameters) {
        List<User> users = this.userService.getAllUsers();
        MessageResources<UserR> result = new MessageResources<>(users.stream().map(user -> userRMapper.map(user)).collect(Collectors.toList()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<UserR>> get(@PathVariable("id") Integer id, UserQ parameters) throws Exception {
        Validate.notNull(id, "Parametro obbligatorio mancante: id.");

        User user = this.userService.findById(id);
        MessageResource<UserR> result = new MessageResource<>(userRMapper.map(user));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<UserR>> create(@RequestBody UserR user) {
        Validate.notNull(user, "Nessuna risorsa da creare passata.");

        User newUser = this.userService.save(this.userMapper.map(user));
        MessageResource<UserR> result = new MessageResource<>(this.userRMapper.map(newUser));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    @PutMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<UserR>> update(@PathVariable("id") Integer id, @RequestBody UserR user) {
        Validate.notNull(user, "Nessuna risorsa da aggiornare passata.");
        Validate.notNull(user.getId(), "Parametro obbligatorio mancante: id user.");
        Validate.isTrue(user.getId().equals(id), "L'id della richiesta e quello del body non coincidono");

        User userToUpate = this.userService.findById(user.getId());
        User updatedUser = this.userService.update(this.userMapper.map(user, userToUpate));
        MessageResource<UserR> result = new MessageResource<>(this.userRMapper.map(updatedUser));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Tramite questo metodo è possibile aggiornare in maniera atomica alcune informazione dell'utente.
     *
     * @param id
     * @param operations
     * @return
     * @throws Exception
     */
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole(T(ApplicationUserRole).ADMIN.getName())")
    public ResponseEntity<MessageResource<UserR>> patch(@PathVariable("id") Integer id, @RequestBody List<PatchOperation> operations) throws Exception {
        Validate.notEmpty(operations, "Nessuna operazione passata come parametro");

        List<Message> messages = new ArrayList<>();
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

        Message message = null;
        if(isToUpdate) {
            user = userService.update(user);
            message = new Message("Aggiornamento effettuato con successo.");
        } else {
            message = new Message("Nessun aggiornamento effettuato.");
        }
        messages.add(message);

        UserR userR = userRMapper.map(user);

        return new ResponseEntity<>(new MessageResource<>(userR, messages), HttpStatus.OK);
    }

}
