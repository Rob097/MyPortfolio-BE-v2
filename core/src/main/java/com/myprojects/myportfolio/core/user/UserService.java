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
import java.util.NoSuchElementException;
import java.util.Optional;

@Service(value="userService")
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Slice<User> findAll(Specification specification, Pageable pageable){

        Slice<User> users = this.userRepository.findAll(specification, pageable);

        return users;
    }

    public User findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("Impossible to found any user with id: " + id));
    }

    public User findByEmail(String email) {
        Validate.notNull(email, "Mandatory parameter is missing: email.");

        User user = this.userRepository.findByEmail(email);
        return user;
    }

    public User save(User userToSave){
        Validate.notNull(userToSave, "Mandatory parameter is missing: user.");

        if(userToSave.getId()!=null) {
            Optional<User> actual = this.userRepository.findById(userToSave.getId());
            Validate.isTrue(!actual.isPresent(), "It already exists an application user with id: " + userToSave.getId());
        }

        User user = this.userRepository.save(userToSave);
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
        User user = this.findByEmail(username);
        return user.getId().equals(id);
    }
    public User getCurrentLoggedInUser(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(username);
        return user;
    }

}

