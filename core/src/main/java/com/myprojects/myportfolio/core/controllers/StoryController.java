package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.dao.*;
import com.myprojects.myportfolio.core.dto.StoryDto;
import com.myprojects.myportfolio.core.mappers.StoryMapper;
import com.myprojects.myportfolio.core.services.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping(path = "/slug/{slug}")
    public ResponseEntity<MessageResource<StoryDto>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = "view", required = false, defaultValue = Normal.name) IView view
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        Story story = storyService.findBy(findByEquals(Story.FIELDS.SLUG.getName(), slug));

        return this.buildSuccessResponse(storyMapper.mapToDto(story, view), view);
    }

    @GetMapping(path = "/slugs/{userId}")
    public ResponseEntity<MessageResources<String>> getUserStoriesSlugs(
            @PathVariable("userId") Integer userId
    ) {
        Validate.notNull(userId, fieldMissing("userId"));

        return this.buildSuccessResponsesOfGenericType(storyService.findSlugsByUserId(userId), Normal.value, new ArrayList<>(), false);
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
            if (operation.getPath().matches("^/diary")) {
                isToUpdate = handleEntityOperation(story.getDiary(), Diary.class, diaryService, operation, story);
            } else if (operation.getPath().matches("^/project")) {
                isToUpdate = handleEntityOperation(story.getProject(), Project.class, projectService, operation, story);
            } else if (operation.getPath().matches("^/education")) {
                isToUpdate = handleEntityOperation(story.getEducation(), Education.class, educationService, operation, story);
            } else if (operation.getPath().matches("^/experience")) {
                isToUpdate = handleEntityOperation(story.getExperience(), Experience.class, experienceService, operation, story);
            }
        }

        if (isToUpdate) {
            story = storyService.update(story);
        }

        return this.buildSuccessResponse(storyMapper.mapToDto(story, Normal.value));
    }

    private <T extends BaseDao> boolean handleEntityOperation(T oldEntity, Class<T> entityClass, WithStoriesServiceI<T> service, PatchOperation operation, Story story) {
        if (operation.getOp() == PatchOperation.Op.replace) {
            service.removeStoriesFromEntity(oldEntity.getId(), new Integer[]{story.getId()});

            T newEntity = service.findById(Integer.parseInt(operation.getValue()));
            ((WithStoriesDao) newEntity).getStories().add(story);
            story.setEntity(((WithStoriesDao) newEntity), entityClass);
            return true;
        } else if (operation.getOp() == PatchOperation.Op.remove) {
            service.removeStoriesFromEntity(oldEntity.getId(), new Integer[]{story.getId()});

            story.setEntity(null, entityClass);
            return true;
        }
        return false;
    }

}
