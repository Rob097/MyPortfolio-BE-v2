package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Story;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.repositories.UserRepository;
import com.myprojects.myportfolio.core.services.skills.UserSkillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service(value = "UserService")
@Transactional
public class UserService extends BaseService<User> implements UserServiceI {

    private final UserRepository userRepository;

    private final DiaryServiceI diaryService;

    private final StoryServiceI storyService;

    private final UserSkillService userSkillService;

    public UserService(UserRepository userRepository, DiaryServiceI diaryService, StoryServiceI storyService, UserSkillService userSkillService) {
        super();
        this.repository = userRepository;

        this.userRepository = userRepository;
        this.diaryService = diaryService;
        this.storyService = storyService;
        this.userSkillService = userSkillService;
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
            // Remove the old skill from the user's list by skillId and userId (to avoid duplicates):
            user.getSkills().removeIf(s -> s.getId().equals(skill.getId()));
            skill.setUser(user);
            this.userSkillService.save(skill);
        });

        return user;
    }

    @Override
    public User update(User user) {
        Validate.notNull(user, super.fieldMissing("user"));

        // if user.getMainStoryId() is not null, check if it's a story of the user:
        if (user.getMainStoryId() != null) {
            Story mainStory = storyService.findById(user.getMainStoryId());
            if (!mainStory.getDiary().getUser().getId().equals(user.getId())) {
                throw new IllegalArgumentException("Story with id " + user.getMainStoryId() + " is not a story of the user " + user.getFullName());
            }
        }

        return super.update(user);
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

}
