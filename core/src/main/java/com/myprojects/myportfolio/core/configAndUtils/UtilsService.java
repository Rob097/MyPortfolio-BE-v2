package com.myprojects.myportfolio.core.configAndUtils;

import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;
import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import com.myprojects.myportfolio.core.newDataModel.dto.*;
import com.myprojects.myportfolio.core.newDataModel.repositories.DiaryRepository;
import com.myprojects.myportfolio.core.newDataModel.repositories.ProjectRepository;
import com.myprojects.myportfolio.core.newDataModel.repositories.StoryRepository;
import com.myprojects.myportfolio.core.newDataModel.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;
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
            } else if (entity instanceof NewStoryDto) {
                NewDiary diary = diaryRepository.findById(((NewStoryDto) entity).getDiaryId()).orElseThrow(() -> new RuntimeException("Diary not found"));
                return currentUser.getId().equals(diary.getUser().getId());
            } else {
                throw new RuntimeException("Unknown entity type");
            }
        }
    }

}
