package com.myprojects.myportfolio.core.controllers;

import com.myprojects.myportfolio.clients.general.messages.IMessage;
import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.core.dao.Attachment;
import com.myprojects.myportfolio.core.dto.AttachmentDto;
import com.myprojects.myportfolio.core.mappers.AttachmentMapper;
import com.myprojects.myportfolio.core.services.AttachmentServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController("AttachmentController")
@RequestMapping("${core-module-basic-path}" + "/attachments")
public class AttachmentController extends UserRelatedBaseController<Attachment, AttachmentDto> {

    private final AttachmentServiceI attachmentService;

    private final AttachmentMapper attachmentMapper;

    public AttachmentController(AttachmentServiceI attachmentService, AttachmentMapper attachmentMapper) {
        super(attachmentService, attachmentMapper);

        this.attachmentService = attachmentService;
        this.attachmentMapper = attachmentMapper;
    }

    @PostMapping("/new")
    public ResponseEntity<?> create(
            @RequestParam(name = "files") MultipartFile[] files
    ) {
        List<Attachment> attachments = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                attachments.add(attachmentService.save(file));
            } catch (Exception e) {
                log.error("Error while saving file", e);
            }
        }

        List<AttachmentDto> attachmentsDto = attachments.stream().map(attachmentMapper::mapToDto).collect(Collectors.toList());
        return this.buildSuccessResponses(attachmentsDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestParam(name = "ids") List<Integer> ids
    ) {
        List<Message> messages = new ArrayList<>();
        for (Integer id : ids) {
            try {
                attachmentService.deleteById(id);
                messages.add(new Message("Attachment with id " + id + " deleted successfully", IMessage.Level.INFO));
            } catch (Exception e) {
                log.error("Error while deleting file", e);
                messages.add(new Message("Error while deleting attachment with id " + id, IMessage.Level.ERROR));
            }
        }

        return this.buildSuccessResponse(null, null, messages);
    }

    @Override
    public ResponseEntity<MessageResource<AttachmentDto>> create(AttachmentDto resource) throws Exception {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public ResponseEntity<MessageResource<AttachmentDto>> update(Integer id, AttachmentDto resource) throws Exception {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public ResponseEntity<MessageResource<AttachmentDto>> delete(Integer id) throws Exception {
        throw new UnsupportedOperationException("Method not implemented");
    }

}
