package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.core.newDataModel.dao.NewStory;
import com.myprojects.myportfolio.core.newDataModel.dto.NewStoryDto;
import com.myprojects.myportfolio.core.newDataModel.mappers.StoryMapper;
import com.myprojects.myportfolio.core.newDataModel.services.StoryServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("newStoryController")
@RequestMapping("${core-module-basic-path}" + "/new/stories")
public class StoryController extends BaseController<NewStory, NewStoryDto> {

    private final StoryServiceI storyService;

    private final StoryMapper storyMapper;

    public StoryController(StoryServiceI storyService, StoryMapper storyMapper) {
        this.service = storyService;
        this.mapper = storyMapper;

        this.storyService = storyService;
        this.storyMapper = storyMapper;
    }

    /**
     * Methods, if not overridden above, are implemented in super class.
     */

    @GetMapping(path = "/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<NewStoryDto>> get(
            @PathVariable("slug") String slug
    ) throws Exception {
        Validate.notNull(slug, fieldMissing("slug"));

        NewStory story = storyService.findBy(findByEquals(NewStory.FIELDS.SLUG.name(), slug));

        return this.buildSuccessResponse(storyMapper.mapToDto(story));
    }

    // TODO:
    // What we could do is implement a generic flow that allow us to:
    // - Add a new Project to an existing story
    // - Add a new Story to an existing Project
    // For all the entities that are related to a Story.
    // Right now, the owners of the relationship with story are the entities like project.
    // So, when we create/update a project we can also create a story but not the other way around.
    // And also when we update a project we have to pass the whole object plus the new story.
    // It would be nice to have a way to just pass the story that we want to add to the project.

}
