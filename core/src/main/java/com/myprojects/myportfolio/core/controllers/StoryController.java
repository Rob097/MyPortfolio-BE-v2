package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.*;
import com.myprojects.myportfolio.core.dto.StoryDto;
import com.myprojects.myportfolio.core.mappers.StoryMapper;
import com.myprojects.myportfolio.core.services.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("StoryController")
@RequestMapping("${core-module-basic-path}" + "/stories")
public class StoryController extends UserRelatedBaseController<Story, StoryDto> {

    private final StoryServiceI storyService;

    private final StoryMapper storyMapper;

    private final DiaryServiceI diaryService;

    private final ProjectServiceI projectService;

    private final EducationServiceI educationService;

    private final ExperienceServiceI experienceService;

    public StoryController(StoryServiceI storyService, StoryMapper storyMapper, DiaryServiceI diaryService, ProjectServiceI projectService, EducationServiceI educationService, ExperienceServiceI experienceService) {
        super(storyService, storyMapper);

        this.storyService = storyService;
        this.storyMapper = storyMapper;
        this.diaryService = diaryService;
        this.projectService = projectService;
        this.educationService = educationService;
        this.experienceService = experienceService;
    }

    /**
     * Methods, if not overridden above, are implemented in super class.
     */

    @GetMapping(path = "/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<StoryDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        Story story = storyService.findBy(findByEquals(Story.FIELDS.SLUG.getName(), slug));

        return this.buildSuccessResponse(storyMapper.mapToDto(story, view), view);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<MessageResource<StoryDto>> patch(
            @PathVariable("id") Integer id,
            @RequestBody List<PatchOperation> operations
    ) throws Exception {
        Validate.notEmpty(operations, "No valid operation was provided.");

        boolean isToUpdate = false;

        Story story = storyService.findById(id);
        if (!utilsService.isOfCurrentUser(storyMapper.mapToDto(story, Normal.value), false)) {
            throw new Exception("You can't edit this story because is not yours.");
        }

        for (PatchOperation operation : operations) {
            if (operation.getPath().matches("^/diary") && operation.getOp() == PatchOperation.Op.replace) {
                Diary oldDiary = story.getDiary();
                diaryService.removeStoriesFromEntity(oldDiary.getId(), new Integer[]{story.getId()});

                Diary newDiary = diaryService.findById(Integer.parseInt(operation.getValue()));
                newDiary.getStories().add(story);
                story.setDiary(newDiary);
                isToUpdate = true;
            } else if (operation.getPath().matches("^/project") && operation.getOp() == PatchOperation.Op.replace) {
                Project oldProject = story.getProject();
                projectService.removeStoriesFromEntity(oldProject.getId(), new Integer[]{story.getId()});

                Project newProject = projectService.findById(Integer.parseInt(operation.getValue()));
                newProject.getStories().add(story);
                story.setProject(newProject);
                isToUpdate = true;
            } else if (operation.getPath().matches("^/education")) {
                Education oldEducation = story.getEducation();
                educationService.removeStoriesFromEntity(oldEducation.getId(), new Integer[]{story.getId()});

                Education newEducation = educationService.findById(Integer.parseInt(operation.getValue()));
                newEducation.getStories().add(story);
                story.setEducation(newEducation);
                isToUpdate = true;
            } else if (operation.getPath().matches("^/experience")) {
                Experience oldExperience = story.getExperience();
                experienceService.removeStoriesFromEntity(oldExperience.getId(), new Integer[]{story.getId()});

                Experience newExperience = experienceService.findById(Integer.parseInt(operation.getValue()));
                newExperience.getStories().add(story);
                story.setExperience(newExperience);
                isToUpdate = true;
            }

        }

        if (isToUpdate) {
            story = storyService.update(story);
        }

        return this.buildSuccessResponse(storyMapper.mapToDto(story, Normal.value));
    }

}
