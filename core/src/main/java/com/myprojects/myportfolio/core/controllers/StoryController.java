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
public class StoryController extends BaseController<Story, StoryDto> {

    private final StoryServiceI storyService;

    private final StoryMapper storyMapper;

    private final DiaryServiceI diaryService;

    private final ProjectServiceI projectService;

    private final EducationServiceI educationService;

    private final ExperienceServiceI experienceService;

    public StoryController(StoryServiceI storyService, StoryMapper storyMapper, DiaryServiceI diaryService, ProjectServiceI projectService, EducationServiceI educationService, ExperienceServiceI experienceService) {
        this.service = storyService;
        this.mapper = storyMapper;

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

        Story story = storyService.findBy(findByEquals(Story.FIELDS.SLUG.name(), slug));

        return this.buildSuccessResponse(storyMapper.mapToDto(story), view);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<MessageResource<StoryDto>> patch(
            @PathVariable("id") Integer id,
            @RequestBody List<PatchOperation> operations
    ) throws Exception {
        Validate.notEmpty(operations, "No valid operation was provided.");

        boolean isToUpdate = false;

        Story story = storyService.findById(id);
        if (!utilsService.isOfCurrentUser(mapper.mapToDto(story), false)) {
            throw new Exception("You can't edit this story because is not yours.");
        }

        for (PatchOperation operation : operations) {
            if (operation.getPath().matches("^/diary") && operation.getOp() == PatchOperation.Op.replace) {
                Diary oldDiary = story.getDiary();
                diaryService.removeStoriesFromDiary(oldDiary.getId(), new Integer[]{story.getId()});

                Diary newDiary = diaryService.findById(Integer.parseInt(operation.getValue()));
                newDiary.getStories().add(story);
                story.setDiary(newDiary);
                isToUpdate = true;
            } else if (operation.getPath().matches("^/project")) {
                Project project = projectService.findById(Integer.parseInt(operation.getValue()));

                if (operation.getOp() == PatchOperation.Op.add) {
                    project.getStories().add(story);
                    story.getProjects().add(project);
                } else if (operation.getOp() == PatchOperation.Op.remove) {
                    project.getStories().remove(story);
                    story.getProjects().remove(project);
                }

                isToUpdate = true;
            } else if (operation.getPath().matches("^/education")) {
                Education education = educationService.findById(Integer.parseInt(operation.getValue()));

                if (operation.getOp() == PatchOperation.Op.add) {
                    education.getStories().add(story);
                    story.getEducations().add(education);
                } else if (operation.getOp() == PatchOperation.Op.remove) {
                    education.getStories().remove(story);
                    story.getEducations().remove(education);
                }

                isToUpdate = true;
            } else if (operation.getPath().matches("^/experience")) {
                Experience experience = experienceService.findById(Integer.parseInt(operation.getValue()));

                if (operation.getOp() == PatchOperation.Op.add) {
                    experience.getStories().add(story);
                    story.getExperiences().add(experience);
                } else if (operation.getOp() == PatchOperation.Op.remove) {
                    experience.getStories().remove(story);
                    story.getExperiences().remove(experience);
                }

                isToUpdate = true;
            }

        }

        if (isToUpdate) {
            story = storyService.update(story);
        }

        return this.buildSuccessResponse(storyMapper.mapToDto(story));
    }

}
