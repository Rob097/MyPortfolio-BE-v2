package com.myprojects.myportfolio.core.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myprojects.myportfolio.core.dao.Story;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.dto.FileTypeEnum;
import com.myprojects.myportfolio.core.files.FileServiceI;
import com.myprojects.myportfolio.core.repositories.UserRepository;
import com.myprojects.myportfolio.core.services.skills.UserSkillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service(value = "UserService")
@Transactional
public class UserService extends BaseService<User> implements UserServiceI {

    private final UserRepository userRepository;

    private final DiaryServiceI diaryService;

    private final StoryServiceI storyService;

    private final UserSkillService userSkillService;

    private final FileServiceI fileService;

    public UserService(UserRepository userRepository, DiaryServiceI diaryService, StoryServiceI storyService, UserSkillService userSkillService, FileServiceI fileService) {
        super();
        this.repository = userRepository;

        this.userRepository = userRepository;
        this.diaryService = diaryService;
        this.storyService = storyService;
        this.userSkillService = userSkillService;
        this.fileService = fileService;
    }

    @Override
    public List<String> findAllSlugs() {
        return this.userRepository.findAllSlugs().orElse(List.of());
    }

    @Override
    public List<String> uploadFiles(List<MultipartFile> files, Integer userId, FileTypeEnum fileType, String language) {
        String commonLog = "uploadFiles(" + userId + ", " + fileType + ", " + language + ")";
        log.info(commonLog + " - BEGIN");

        User user = this.findById(userId);
        List<String> urls = new ArrayList<>();

        try {
            switch (fileType) {
                case PROFILE_IMAGE -> {

                    // first we get from user customization the old profile image url, we extract the file name and we delete the old file:
                    String oldProfileImageUrl = user.getStringFromCustomizations(User.CustomizationsKeysEnum.PROFILE_IMAGE.getKey());
                    if (oldProfileImageUrl != null) {
                        String oldProfileImageFilePath = fileService.getFileNameFromUrl(oldProfileImageUrl);
                        log.info(commonLog + " - Deleting old profile image file: " + oldProfileImageFilePath);
                        fileService.delete(oldProfileImageFilePath);
                    }

                    // In this case we manage only one file.
                    String fileName = fileService.getCompleteFileName(FileTypeEnum.PROFILE_IMAGE.getFolderName(), FileTypeEnum.PROFILE_IMAGE.getFileName(userId), files.get(0).getOriginalFilename());
                    String url = fileService.getImageUrl(fileService.save(fileName, files.get(0)));
                    user.addToCustomizations(User.CustomizationsKeysEnum.PROFILE_IMAGE.getKey(), url);
                    urls.add(url);
                }
                case CURRICULUM_VITAE -> {

                    JsonObject customizations = new Gson().fromJson(user.getCustomizations(), JsonObject.class);
                    boolean doesCVExist = customizations.has(User.CustomizationsKeysEnum.CURRICULUM_VITAE.getKey());
                    JsonObject cvs = doesCVExist ? customizations.getAsJsonObject(User.CustomizationsKeysEnum.CURRICULUM_VITAE.getKey()) : new JsonObject();

                    String oldCurriculumVitaeUrl = cvs.has(language) ? cvs.get(language).getAsString() : null;
                    if (oldCurriculumVitaeUrl != null) {
                        String oldCurriculumVitaeFilePath = fileService.getFileNameFromUrl(oldCurriculumVitaeUrl);
                        log.info(commonLog + " - Deleting old curriculum vitae file: " + oldCurriculumVitaeFilePath);
                        fileService.delete(oldCurriculumVitaeFilePath);
                    }

                    // In this case we manage only one file.
                    String fileName = fileService.getCompleteFileName(FileTypeEnum.CURRICULUM_VITAE.getFolderName(), FileTypeEnum.CURRICULUM_VITAE.getFileName(userId, language), files.get(0).getOriginalFilename());
                    String url = fileService.getImageUrl(fileService.save(fileName, files.get(0)));

                    // Update the user's customizations:
                    cvs.addProperty(language, url);
                    customizations.add(User.CustomizationsKeysEnum.CURRICULUM_VITAE.getKey(), cvs);
                    user.setCustomizations(customizations.toString());

                    urls.add(url);
                }
                default -> throw new IllegalArgumentException("File type " + fileType + " is not supported");
            }
        } catch (IOException e) {
            log.error(commonLog + " - ERROR: " + e.getMessage());
            return List.of();
        }

        log.info(commonLog + " - URLS: " + urls);

        // Update user with the new customization:
        this.update(user);

        log.info(commonLog + " - END");
        return urls;
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
