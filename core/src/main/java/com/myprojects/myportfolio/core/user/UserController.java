package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.clients.user.UserR;
import com.myprojects.myportfolio.core.user.mappers.UserMapper;
import com.myprojects.myportfolio.core.user.mappers.UserRMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("core/api/v1/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRMapper userRMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping()
    public List<UserR> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        List<UserR> result = users.stream().map(user -> userRMapper.map(user)).collect(Collectors.toList());
        return result;
    }

    @PostMapping()
    public UserR saveUser(@RequestBody UserR user) {
        User newUser = this.userService.save(this.userMapper.map(user));
        UserR result = this.userRMapper.map(newUser);

        return result;
    }

}
