package com.myprojects.myportfolio.clients.user;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "core",
        path = "api/core/users"
)
public interface UserClient {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MessageResource<UserR>> create(@RequestBody UserR user);

}
