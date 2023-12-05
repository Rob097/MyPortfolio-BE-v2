package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.core.dto.FeedbackDto;
import com.myprojects.myportfolio.core.mappers.FeedbackMapper;
import com.myprojects.myportfolio.core.services.FeedbackServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("FeedbackController")
@RequestMapping("${core-module-basic-path}" + "/feedback")
public class FeedbackController extends SimpleController<FeedbackDto> {

    private final FeedbackServiceI feedbackService;

    private final FeedbackMapper feedbackMapper;

    public FeedbackController(FeedbackServiceI feedbackService, FeedbackMapper feedbackMapper) {
        this.feedbackService = feedbackService;
        this.feedbackMapper = feedbackMapper;
    }

    @PostMapping()
    public ResponseEntity<MessageResources<FeedbackDto>> save(
            @RequestBody FeedbackDto feedback
    ) {
        log.info("New feedback: {}", feedback);

        feedbackService.save(feedbackMapper.mapToDao(feedback));

        return ResponseEntity.ok().build();
    }

}