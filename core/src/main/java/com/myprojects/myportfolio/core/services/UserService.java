package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.repositories.UserRepository;
import com.myprojects.myportfolio.core.services.skills.UserSkillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service(value = "UserService")
@Transactional
public class UserService extends BaseService<User> implements UserServiceI {

    private final UserRepository userRepository;

    private final DiaryServiceI diaryService;

    private final UserSkillService userSkillService;

    public UserService(UserRepository userRepository, DiaryServiceI diaryService, UserSkillService userSkillService) {
        super();
        this.repository = userRepository;

        this.userRepository = userRepository;
        this.diaryService = diaryService;
        this.userSkillService = userSkillService;
    }

    @Override
    public User findBy(Specification<User> specification) {

        List<User> all = this.userRepository.findAll(specification);
        if (!all.isEmpty()) {
            return all.get(0);
        }

        return null;
    }

    @Override
    public List<String> findAllSlugs() {
        return this.userRepository.findAllSlugs().orElse(List.of());
    }

    @Override
    public User save(User user) {
        Validate.notNull(user, super.fieldMissing("user"));

        this.userRepository.findByEmail(user.getEmail()).ifPresent(existingUser -> {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        });

        // Save user
        super.save(user);

        // Save user's diaries (and story for cascade):
        user.getDiaries().forEach(diary -> {
            if (diary.getId() == null) {
                diary.setUser(user);
                this.diaryService.save(diary);
            }
        });

        // Save user's skills:
        user.getSkills().forEach(skill -> {
            user.getSkills().remove(skill); // Remove the old skill from the user's list (to avoid duplicates)
            skill.setUser(user);
            this.userSkillService.save(skill);
        });

        return user;
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
