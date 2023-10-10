package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.*;
import com.myprojects.myportfolio.core.dto.NewStoryDto;
import com.myprojects.myportfolio.core.mappers.StoryMapper;
import com.myprojects.myportfolio.core.services.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("newStoryController")
@RequestMapping("${core-module-basic-path}" + "/new/stories")
public class StoryController extends BaseController<NewStory, NewStoryDto> {

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
    public ResponseEntity<MessageResource<NewStoryDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        NewStory story = storyService.findBy(findByEquals(NewStory.FIELDS.SLUG.name(), slug));

        return this.buildSuccessResponse(storyMapper.mapToDto(story), view);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<MessageResource<NewStoryDto>> patch(
            @PathVariable("id") Integer id,
            @RequestBody List<PatchOperation> operations
    ) throws Exception {
        Validate.notEmpty(operations, "No valid operation was provided.");

        boolean isToUpdate = false;

        NewStory story = storyService.findById(id);
        if (!utilsService.isOfCurrentUser(mapper.mapToDto(story), false)) {
            throw new Exception("You can't edit this story because is not yours.");
        }

        for (PatchOperation operation : operations) {
            if (operation.getPath().matches("^/diary") && operation.getOp() == PatchOperation.Op.replace) {
                NewDiary oldDiary = story.getDiary();
                diaryService.removeStoriesFromDiary(oldDiary.getId(), new Integer[]{story.getId()});

                NewDiary newDiary = diaryService.findById(Integer.parseInt(operation.getValue()));
                newDiary.getStories().add(story);
                story.setDiary(newDiary);
                isToUpdate = true;
            } else if (operation.getPath().matches("^/project")) {
                NewProject project = projectService.findById(Integer.parseInt(operation.getValue()));

                if (operation.getOp() == PatchOperation.Op.add) {
                    project.getStories().add(story);
                    story.getProjects().add(project);
                } else if (operation.getOp() == PatchOperation.Op.remove) {
                    project.getStories().remove(story);
                    story.getProjects().remove(project);
                }

                isToUpdate = true;
            } else if (operation.getPath().matches("^/education")) {
                NewEducation education = educationService.findById(Integer.parseInt(operation.getValue()));

                if (operation.getOp() == PatchOperation.Op.add) {
                    education.getStories().add(story);
                    story.getEducations().add(education);
                } else if (operation.getOp() == PatchOperation.Op.remove) {
                    education.getStories().remove(story);
                    story.getEducations().remove(education);
                }

                isToUpdate = true;
            } else if (operation.getPath().matches("^/experience")) {
                NewExperience experience = experienceService.findById(Integer.parseInt(operation.getValue()));

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
