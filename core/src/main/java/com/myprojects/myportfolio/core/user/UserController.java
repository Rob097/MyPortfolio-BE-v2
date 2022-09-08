package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.clients.user.UserR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRMapper userRMapper;

    @GetMapping()
    public List<UserR> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        List<UserR> result = users.stream().map(user -> userRMapper.map(user)).collect(Collectors.toList());
        return result;
    }

}
