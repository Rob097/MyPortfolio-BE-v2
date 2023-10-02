package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import com.myprojects.myportfolio.core.newDataModel.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service(value = "newUserService")
@Transactional
public class UserService extends BaseService<NewUser> implements UserServiceI {

    private final UserRepository userRepository;

    private final DiaryServiceI diaryService;

    public UserService(UserRepository userRepository, DiaryServiceI diaryService) {
        super();
        this.repository = userRepository;

        this.userRepository = userRepository;
        this.diaryService = diaryService;
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

        // Save user
        NewUser savedUser = super.save(user);

        // Save user's diaries (and story for cascade):
        user.getDiaries().forEach(diary -> {
            if(diary.getId()==null) {
                diary.setUser(savedUser);
                this.diaryService.save(diary);
            }
        });

        return savedUser;
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
