package com.myprojects.myportfolio.core.configAndUtils;

import com.myprojects.myportfolio.core.newDataModel.dao.*;
import com.myprojects.myportfolio.core.newDataModel.dto.*;
import com.myprojects.myportfolio.core.newDataModel.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UtilsService implements UtilsServiceI {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private StoryRepository storyRepository;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    @Override
    public String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
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

    /**
     * Method used in PreAuthorize annotation to check if current user is owner of entity
     * @param entity: entity to check
     * @param isCreate: true if the check is to perform on an entity that is being created
     * @return true if current user is owner of entity
     * @param <T>: type of dto
     */
    @Override
    public <T extends BaseDto> boolean isOfCurrentUser(T entity, boolean isCreate) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NewUser currentUser = this.userRepository.findByEmail(username).orElse(null);
        if (currentUser == null)
            return false;

        if (!isCreate) {
            if (entity instanceof NewUserDto) {
                return currentUser.getId().equals(entity.getId());
            } else if (entity instanceof NewDiaryDto) {
                NewDiary diary = diaryRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Diary not found"));
                return currentUser.getId().equals(diary.getUser().getId());
            } else if (entity instanceof NewProjectDto) {
                NewProject project = projectRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Project not found"));
                return currentUser.getId().equals(project.getUser().getId());
            } else if (entity instanceof NewEducationDto) {
                NewEducation education = educationRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Education not found"));
                return currentUser.getId().equals(education.getUser().getId());
            } else if (entity instanceof NewExperienceDto) {
                NewExperience experience = experienceRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Experience not found"));
                return currentUser.getId().equals(experience.getUser().getId());
            } else if (entity instanceof NewStoryDto) {
                NewStory story = storyRepository.findById(entity.getId()).orElseThrow(() -> new RuntimeException("Story not found"));
                return currentUser.getId().equals(story.getDiary().getUser().getId());
            } else {
                throw new RuntimeException("Unknown entity type");
            }
        } else {
            if (entity instanceof NewUserDto) {
                return true;
            } else if (entity instanceof NewDiaryDto) {
                return currentUser.getId().equals(((NewDiaryDto) entity).getUserId());
            } else if (entity instanceof NewProjectDto) {
                return currentUser.getId().equals(((NewProjectDto) entity).getUserId());
            } else if (entity instanceof NewEducationDto) {
                return currentUser.getId().equals(((NewEducationDto) entity).getUserId());
            } else if (entity instanceof NewExperienceDto) {
                return currentUser.getId().equals(((NewExperienceDto) entity).getUserId());
            } else if (entity instanceof NewStoryDto) {
                NewDiary diary = diaryRepository.findById(((NewStoryDto) entity).getDiaryId()).orElseThrow(() -> new RuntimeException("Diary not found"));
                return currentUser.getId().equals(diary.getUser().getId());
            } else {
                throw new RuntimeException("Unknown entity type");
            }
        }
    }

}
