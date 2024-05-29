package com.myprojects.myportfolio.core.configAndUtils;

import com.myprojects.myportfolio.core.dao.*;
import com.myprojects.myportfolio.core.dto.*;
import com.myprojects.myportfolio.core.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UtilsService implements UtilsServiceI {

    private final UserRepository userRepository;

    private final DiaryRepository diaryRepository;

    private final ProjectRepository projectRepository;

    private final EducationRepository educationRepository;

    private final ExperienceRepository experienceRepository;

    private final StoryRepository storyRepository;

    public UtilsService(UserRepository userRepository, DiaryRepository diaryRepository, ProjectRepository projectRepository, EducationRepository educationRepository, ExperienceRepository experienceRepository, StoryRepository storyRepository) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
        this.projectRepository = projectRepository;
        this.educationRepository = educationRepository;
        this.experienceRepository = experienceRepository;
        this.storyRepository = storyRepository;
    }

    /**
     * Method used to check if an id is the same as the one of the current logged-in user
     */
    @Override
    public boolean hasId(Integer id) {
        if (isJUnitTest())
            return true;

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = this.userRepository.findByEmail(username);
        return user.isPresent() && user.get().getId().equals(id);
    }

    @Override
    public User getCurrentLoggedInUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findByEmail(username).orElse(null);
    }

    /**
     * Method used in PreAuthorize annotation to check if current user is owner of entity
     * @param entity: entity to check
     * @param isCreate: true if the check is to perform on an entity that is being created
     * @return true if current user is owner of entity
     * @param <T>: type of dto
     */
    @Override
    public <T extends BaseDto> boolean isOfCurrentUser(T entity, boolean isCreate) {
        if (isJUnitTest())
            return true;

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = this.userRepository.findByEmail(username).orElse(null);
        if (currentUser == null && !(isCreate && entity instanceof UserDto))
            return false;

        if (!isCreate) {
            if (entity instanceof UserDto) {
                return currentUser.getId().equals(entity.getId());
            } else if (entity instanceof DiaryDto) {
                Diary diary = diaryRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Diary not found"));
                return currentUser.getId().equals(diary.getUser().getId());
            } else if (entity instanceof ProjectDto) {
                Project project = projectRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Project not found"));
                return currentUser.getId().equals(project.getUser().getId());
            } else if (entity instanceof EducationDto) {
                Education education = educationRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Education not found"));
                return currentUser.getId().equals(education.getUser().getId());
            } else if (entity instanceof ExperienceDto) {
                Experience experience = experienceRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Experience not found"));
                return currentUser.getId().equals(experience.getUser().getId());
            } else if (entity instanceof StoryDto) {
                Story story = storyRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Story not found"));
                return currentUser.getId().equals(story.getDiary().getUser().getId());
            } else {
                throw new RuntimeException("Unknown entity type");
            }
        } else {
            if (entity instanceof UserDto) {
                return true;
            } else if (entity instanceof DiaryDto) {
                return currentUser.getId().equals(((DiaryDto) entity).getUserId());
            } else if (entity instanceof ProjectDto) {
                return currentUser.getId().equals(((ProjectDto) entity).getUserId());
            } else if (entity instanceof EducationDto) {
                return currentUser.getId().equals(((EducationDto) entity).getUserId());
            } else if (entity instanceof ExperienceDto) {
                return currentUser.getId().equals(((ExperienceDto) entity).getUserId());
            } else if (entity instanceof StoryDto) {
                Diary diary = diaryRepository.findById(((StoryDto) entity).getDiaryId()).orElseThrow(() -> new RuntimeException("Diary not found"));
                return currentUser.getId().equals(diary.getUser().getId());
            } else {
                throw new RuntimeException("Unknown entity type");
            }
        }
    }

    /**
     * Method used in PreAuthorize annotation to check if current user is owner of entity
     * @param entity: entity to check
     * @param isCreate: true if the check is to perform on an entity that is being created
     * @return true if current user is owner of entity
     * @param <T>: type of dto
     */
    @Override
    public <T extends BaseDao> boolean isOfCurrentUser(T entity, boolean isCreate) {
        if (isJUnitTest())
            return true;

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = this.userRepository.findByEmail(username).orElse(null);
        if (currentUser == null && !(isCreate && entity instanceof User))
            return false;

        if (!isCreate) {
            if (entity instanceof User) {
                return currentUser.getId().equals(entity.getId());
            } else if (entity instanceof Diary) {
                Diary diary = diaryRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Diary not found"));
                return currentUser.getId().equals(diary.getUser().getId());
            } else if (entity instanceof Project) {
                Project project = projectRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Project not found"));
                return currentUser.getId().equals(project.getUser().getId());
            } else if (entity instanceof Education) {
                Education education = educationRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Education not found"));
                return currentUser.getId().equals(education.getUser().getId());
            } else if (entity instanceof Experience) {
                Experience experience = experienceRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Experience not found"));
                return currentUser.getId().equals(experience.getUser().getId());
            } else if (entity instanceof Story) {
                Story story = storyRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Story not found"));
                return currentUser.getId().equals(story.getDiary().getUser().getId());
            } else {
                throw new RuntimeException("Unknown entity type");
            }
        } else {
            if (entity instanceof User) {
                return true;
            } else if (entity instanceof Diary) {
                return currentUser.getId().equals(((Diary) entity).getUserId());
            } else if (entity instanceof Project) {
                return currentUser.getId().equals(((Project) entity).getUserId());
            } else if (entity instanceof Education) {
                return currentUser.getId().equals(((Education) entity).getUserId());
            } else if (entity instanceof Experience) {
                return currentUser.getId().equals(((Experience) entity).getUserId());
            } else if (entity instanceof Story) {
                Diary diary = diaryRepository.findById(((Story) entity).getDiaryId()).orElseThrow(() -> new RuntimeException("Diary not found"));
                return currentUser.getId().equals(diary.getUser().getId());
            } else {
                throw new RuntimeException("Unknown entity type");
            }
        }
    }

    private boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }

}
