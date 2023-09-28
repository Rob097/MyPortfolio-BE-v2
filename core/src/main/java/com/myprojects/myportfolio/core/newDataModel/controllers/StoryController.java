package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.newDataModel.dao.NewDiary;
import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.dto.NewStoryDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.StoryMapper;
import com.myprojects.myportfolio.core.newDataModel.services.DiaryServiceI;
import com.myprojects.myportfolio.core.newDataModel.services.ProjectServiceI;
import com.myprojects.myportfolio.core.newDataModel.services.StoryServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public StoryController(StoryServiceI storyService, StoryMapper storyMapper, DiaryServiceI diaryService, ProjectServiceI projectService) {
        this.service = storyService;
        this.mapper = storyMapper;

        this.storyService = storyService;
        this.storyMapper = storyMapper;
        this.diaryService = diaryService;
        this.projectService = projectService;
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
                NewDiary newDiary = diaryService.findById(Integer.parseInt(operation.getValue()));
                newDiary.getStories().add(story);
                story.setDiary(newDiary);
                isToUpdate = true;

                NewDiary oldDiary = story.getDiary();
                diaryService.removeStoriesFromDiary(oldDiary.getId(), new Integer[]{story.getId()});
            }
            if (operation.getPath().matches("^/project")) {
                NewProject project = projectService.findById(Integer.parseInt(operation.getValue()));

                if (operation.getOp() == PatchOperation.Op.add) {
                    project.getStories().add(story);
                    story.getProjects().add(project);
                } else if (operation.getOp() == PatchOperation.Op.remove) {
                    project.getStories().remove(story);
                    story.getProjects().remove(project);
                }

                isToUpdate = true;
            }
        }

        if (isToUpdate) {
            story = storyService.update(story);
        }

        return this.buildSuccessResponse(storyMapper.mapToDto(story));
    }


    // TODO:
    // What we could do is implement a generic flow that allow us to:
    // - Add a new Project to an existing story --> Create project with POST passing the storyId in body -- ERROR:
    // - Add a new Story to an existing Project --> Create story with POST and then add it to the project with PATCH -- ERROR:
    // - Add an existing project to an existing story --> PATCH
    // - Add an existing story to an existing project --> PATCH
    // For all the entities that are related to a Story.
    // Right now, the owners of the relationship with story are the entities like project.
    // So, when we create/update a project we can also create a story but not the other way around.
    // And also when we update a project we have to pass the whole object plus the new story.
    // It would be nice to have a way to just pass the story that we want to add to the project.

    // PROBLEMI:
    // - Post project con stories solo con id quindi esistenti
    //     - detached entity passed to persist --> SOLUTION CHANGE CASCADE TYPE TO MERGE
    // - Put project con stories (sia con id quindi esistenti che senza quindi nuove)
    //     - org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing --> SOLUTION = @Transactional in service layer

}
