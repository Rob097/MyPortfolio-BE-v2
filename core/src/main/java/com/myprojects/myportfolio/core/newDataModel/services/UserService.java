package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import com.myprojects.myportfolio.core.newDataModel.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "newUserService")
@Transactional
public class UserService extends BaseService<NewUser> implements UserServiceI {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super();
        this.repository = userRepository;

        this.userRepository = userRepository;
    }

    @Override
    public NewUser findBy(Specification<NewUser> specification) {

        List<NewUser> all = this.userRepository.findAll(specification);
        if (!all.isEmpty()) {
            return all.get(0);
        }

        return null;
    }

    @Override
    public NewUser save(NewUser user) {
        Validate.notNull(user, super.fieldMissing("user"));

        this.userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        });

        return super.save(user);
    }

    @Override
    public NewUser update(NewUser user) {
        Validate.notNull(user, super.fieldMissing("user"));

        return super.update(user);
    }

    /**
     * Method used to check if an id is the same as the one of the current logged-in user
     */
    @Override
    public boolean hasId(Integer id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<NewUser> user = this.userRepository.findByEmail(username);
        return user.isPresent() && user.get().getId().equals(id);
    }

    @Override
    public NewUser getCurrentLoggedInUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findByEmail(username).orElse(null);
    }


    /**********************/
    /*** Private Methods **/
    /**********************/

}
