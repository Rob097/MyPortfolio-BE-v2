package com.myprojects.myportfolio.auth.clients;

import com.myprojects.myportfolio.auth.dto.CoreUser;
import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.SetUpRequest;
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
    ResponseEntity<MessageResource<CoreUser>> create(@RequestBody CoreUser user);

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResource<CoreUser>> update(@PathVariable("id") Integer id, @RequestBody CoreUser user);

    @PutMapping(path = "/{id}/setup")
    ResponseEntity<MessageResource<CoreUser>> setUp(@PathVariable("id") Integer id, @RequestBody SetUpRequest request) throws Exception;

    @PutMapping(path = "/patch/{id}")
    ResponseEntity<MessageResource<CoreUser>> patch(@PathVariable("id") Integer id, @RequestBody List<PatchOperation> operations) throws Exception;

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResource<CoreUser>> delete(@PathVariable("id") Integer id);

    @GetMapping(path = "/fromAuth/getNextId", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResource<Integer>> getNextId();

}
