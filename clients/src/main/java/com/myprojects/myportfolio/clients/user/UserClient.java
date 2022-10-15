package com.myprojects.myportfolio.clients.user;

import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value = "core",
        path = "api/core/users"
)
public interface UserClient {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResource<UserR>> create(@RequestBody UserR user);

    @PutMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResource<UserR>> update(@PathVariable("id") Integer id, @RequestBody UserR user);

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResource<UserR>> patch(@PathVariable("id") Integer id, @RequestBody List<PatchOperation> operations) throws Exception;

    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResource<UserR>> delete(@PathVariable("id") Integer id);

}
