package com.myprojects.myportfolio.core.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        List<User> all = this.userRepository.findAll();
        return all;
    }

    public User save(User u){
        User user = this.userRepository.save(u);
        return user;
    }

}

