package com.myprojects.myportfolio.core.story;

import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.story.StoryR;
import com.myprojects.myportfolio.core.story.mappers.StoryMapper;
import com.myprojects.myportfolio.core.story.mappers.StoryRMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("${core-module-basic-path}" + "/stories")
public class StoryController implements IController<StoryR> {

    private final StoryServiceI storyService;

    private final StoryRMapper storyRMapper;

    private final StoryMapper storyMapper;

    private final HttpServletRequest httpServletRequest;

    public StoryController(StoryServiceI storyService, StoryRMapper storyRMapper, StoryMapper storyMapper, HttpServletRequest httpServletRequest) {
        this.storyService = storyService;
        this.storyRMapper = storyRMapper;
        this.storyMapper = storyMapper;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResources<StoryR>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view,
            Pageable pageable
    ) throws Exception {
        Specification<Story> specifications = this.defineFiltersAndStoreView(filters, view, this.httpServletRequest);

        Slice<Story> stories = storyService.findAll(specifications, pageable);

        return this.buildSuccessResponses(stories.map(storyRMapper::map));
    }

    @Override
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<StoryR>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(id, "Mandatory parameter is missing: id.");
        this.storeRequestView(view, httpServletRequest);

        Story story = storyService.findById(id);

        return this.buildSuccessResponse(storyRMapper.map(story));
    }

    @GetMapping(path="/slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<StoryR>> get(
            @PathVariable("slug") String slug,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(slug, "Mandatory parameter is missing: slug.");
        this.storeRequestView(view, httpServletRequest);

        Story story = storyService.findBySlug(slug);

        return this.buildSuccessResponse(storyRMapper.map(story));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<StoryR>> create(@RequestBody StoryR story) throws Exception {
        Validate.notNull(story, "No valid resource was provided..");

        Story newUser = storyService.save(storyMapper.map(story));
        return this.buildSuccessResponse(storyRMapper.map(newUser));
    }

    @Override
    @PutMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<StoryR>> update(@PathVariable("id") Integer id, @RequestBody StoryR story) throws Exception {
        Validate.notNull(story, "No valid resource to update was provided.");
        Validate.notNull(story.getId(), "Mandatory parameter is missing: id story.");
        Validate.isTrue(story.getId().equals(id), "The request's id and the body's id are different.");

        Story storyToUpate = storyService.findById(story.getId());
        Story updatedUser = storyService.update(storyMapper.map(story, storyToUpate));
        return this.buildSuccessResponse(storyRMapper.map(updatedUser));
    }

    @Override
    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<StoryR>> delete(@PathVariable("id") Integer id) throws Exception {
        Validate.notNull(id, "No valid parameters were provided.");

        Story storyToDelete = storyService.findById(id);
        Validate.notNull(storyToDelete, "No valid story found with id " + id);

        storyService.delete(storyToDelete);
        return this.buildSuccessResponse(storyRMapper.map(storyToDelete));
    }
}
