package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
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
public class UserService implements UserServiceI{

    private final UserRepository userRepository;

    private final UtilsServiceI utilsService;

    public UserService(UserRepository userRepository, UtilsServiceI utilsService) {
        this.userRepository = userRepository;
        this.utilsService = utilsService;
    }

    @Override
    public Slice<User> findAll(Specification<User> specification, Pageable pageable){

        return this.userRepository.findAll(specification, pageable);
    }

    @Override
    public User findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("Impossible to found any user with id: " + id));
    }

    @Override
    public User findBySlug(String slug) {
        Validate.notNull(slug, "Mandatory parameter is missing: slug.");

        Optional<User> user = this.userRepository.findBySlug(slug);
        return user.orElseThrow(() -> new NoSuchElementException("Impossible to found any user with slug: " + slug));
    }

    @Override
    public User findByEmail(String email) {
        Validate.notNull(email, "Mandatory parameter is missing: email.");

        return this.userRepository.findByEmail(email);
    }

    @Override
    public User save(User userToSave){
        Validate.notNull(userToSave, "Mandatory parameter is missing: user.");

        if(userToSave.getId()!=null) {
            Optional<User> actual = this.userRepository.findById(userToSave.getId());
            Validate.isTrue(actual.isEmpty(), "It already exists an application user with id: " + userToSave.getId());
        }

        userToSave.setSlug(generateSlug(userToSave));

        return this.userRepository.save(userToSave);
    }

    @Override
    public User update(User userToUpdate){
        Validate.notNull(userToUpdate, "Mandatory parameter is missing: user.");
        Validate.notNull(userToUpdate.getId(), "Mandatory parameter is missinge: id user.");

        if(userToUpdate.getSlug()==null || userToUpdate.getSlug().isEmpty()) {
            userToUpdate.setSlug(generateSlug(userToUpdate));
        }

        return this.userRepository.save(userToUpdate);
    }

    @Override
    public void delete(User userToDelete){
        Validate.notNull(userToDelete, "Mandatory parameter is missing: user.");

        this.userRepository.delete(userToDelete);
    }

    /**Method used to check if an id is the same as the one of the current logged-in user*/
    @Override
    public boolean hasId(Integer id){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.findByEmail(username);
        return user.getId().equals(id);
    }

    @Override
    public User getCurrentLoggedInUser(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findByEmail(username);
    }

    private String generateSlug(User user) {
        boolean isDone = false;
        int index = 0;
        String slug;

        do {
            String appendix = index == 0 ? "" : ("-"+index);
            slug = utilsService.toSlug(user.getFirstName() + " " + user.getLastName() + appendix);

            Optional<User> existingUser = userRepository.findBySlug(slug);

            if(existingUser.isPresent()) {
                index++;
            } else {
                isDone = true;
            }
        } while (!isDone);

        return slug;

    }

}

