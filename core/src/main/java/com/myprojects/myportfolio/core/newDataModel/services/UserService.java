package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
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

    private final UtilsServiceI utilsService;

    public UserService(UserRepository userRepository, UtilsServiceI utilsService) {
        super();
        this.userRepository = userRepository;
        this.utilsService = utilsService;
        this.repository = userRepository;
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

        user.setSlug(generateSlug(user));
        return super.save(user);
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
    private String generateSlug(NewUser user) {
        boolean isDone = false;
        int index = 0;
        String slug;

        do {
            String appendix = index == 0 ? "" : ("-" + index);
            slug = utilsService.toSlug(user.getFirstName() + " " + user.getLastName() + appendix);

            Optional<NewUser> existingUser = userRepository.findBySlug(slug);

            if (existingUser.isPresent()) {
                index++;
            } else {
                isDone = true;
            }
        } while (!isDone);

        return slug;

    }
}
