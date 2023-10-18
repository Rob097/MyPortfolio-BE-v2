package com.myprojects.myportfolio.clients;

import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.dto.CoreUser;
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

    @PutMapping(path = "/patch/{id}")
    ResponseEntity<MessageResource<CoreUser>> patch(@PathVariable("id") Integer id, @RequestBody List<PatchOperation> operations) throws Exception;

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResource<CoreUser>> delete(@PathVariable("id") Integer id);

    @GetMapping(path = "/fromAuth/getNextId", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResource<Integer>> getNextId();

}
