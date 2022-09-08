package com.myprojects.myportfolio.clients.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        value = "user",
        path = "api/v1/users"
)
public interface UserClient {

    @GetMapping()
    List<UserR> getAllUsers();

}
