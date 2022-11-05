package com.myprojects.myportfolio.core.user;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service(value="userService")
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        List<User> all = this.userRepository.findAll();
        return all;
    }

    public Slice<User> findAll(Specification specification, Pageable pageable){

        Slice<User> users = this.userRepository.findAll(specification, pageable);

        return users;
    }

    public User findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("Impossible to found any user with id: " + id));
    }

    public User save(User u){
        Validate.notNull(u, "Mandatory parameter is missing: user.");

        if(u.getId()!=null) {
            Optional<User> actual = this.userRepository.findById(u.getId());
            Validate.isTrue(!actual.isPresent(), "It already exists an application user with id: " + u.getId());
        }

        User user = this.userRepository.save(u);
        return user;
    }

    public User update(User userToUpdate){
        Validate.notNull(userToUpdate, "Mandatory parameter is missing: user.");
        Validate.notNull(userToUpdate.getId(), "Mandatory parameter is missinge: id user.");

        return this.userRepository.save(userToUpdate);
    }

    public void delete(User userToDelete){
        Validate.notNull(userToDelete, "Mandatory parameter is missing: user.");

        this.userRepository.delete(userToDelete);
    }

    /**Method used to check if an id is the same as the one of the current logged-in user*/
    public boolean hasId(Integer id){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(username);
        return user.getId().equals(id);
    }

}

