package com.myprojects.myportfolio.core.diary;

import com.myprojects.myportfolio.clients.diary.DiaryR;
import com.myprojects.myportfolio.clients.general.IController;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.diary.mappers.DiaryMapper;
import com.myprojects.myportfolio.core.diary.mappers.DiaryRMapper;
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
@RequestMapping("${core-module-basic-path}" + "/diaries")
public class DiaryController implements IController<DiaryR> {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private DiaryRMapper diaryRMapper;

    @Autowired
    private DiaryMapper diaryMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResources<DiaryR>> find(
            @RequestParam(name = FILTERS, required = false) String filters,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view,
            Pageable pageable
    ) throws Exception {
        Specification<Diary> specifications = this.defineFiltersAndStoreView(filters, view, this.httpServletRequest);

        Slice<Diary> diaries = diaryService.findAll(specifications, pageable);

        return this.buildSuccessResponses(diaries.map(diary -> diaryRMapper.map(diary)));
    }

    @Override
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<DiaryR>> get(
            @PathVariable("id") Integer id,
            @RequestParam(name = VIEW, required = false, defaultValue = DEFAULT_VIEW_NAME) IView view
    ) throws Exception {
        Validate.notNull(id, "Mandatory parameter is missing: id.");
        this.storeRequestView(view, httpServletRequest);

        Diary diary = diaryService.findById(id);

        return this.buildSuccessResponse(diaryRMapper.map(diary));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<DiaryR>> create(@RequestBody DiaryR diary) {
        Validate.notNull(diary, "No valid resource was provided..");

        Diary newDiary = diaryService.save(diaryMapper.map(diary));
        return this.buildSuccessResponse(diaryRMapper.map(newDiary));
    }

    @Override
    @PutMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<DiaryR>> update(@PathVariable("id") Integer id, @RequestBody DiaryR diary) {
        Validate.notNull(diary, "No valid resource to update was provided.");
        Validate.notNull(diary.getId(), "Mandatory parameter is missing: id diary.");
        Validate.isTrue(diary.getId().equals(id), "The request's id and the body's id are different.");

        Diary diaryToUpate = diaryService.findById(diary.getId());
        Diary updatedDiary = diaryService.update(diaryMapper.map(diary, diaryToUpate));
        return this.buildSuccessResponse(diaryRMapper.map(updatedDiary));
    }

    @Override
    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResource<DiaryR>> delete(@PathVariable("id") Integer id) {
        Validate.notNull(id, "No valid parameters were provided.");

        Diary diaryToDelete = diaryService.findById(id);
        Validate.notNull(diaryToDelete, "No valid diary found with id " + id);

        diaryService.delete(diaryToDelete);
        return this.buildSuccessResponse(diaryRMapper.map(diaryToDelete));
    }
}
