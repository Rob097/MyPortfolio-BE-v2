package com.myprojects.myportfolio.core.files;

import com.myprojects.myportfolio.clients.general.messages.IMessage;
import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.core.controllers.SimpleController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController("FilesController")
@RequestMapping("${core-module-basic-path}" + "/files")
public class FilesController extends SimpleController<String> {

    private final FileServiceI fileService;

    public FilesController(FileServiceI fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam(name = "file") MultipartFile[] files) {

        String imageUrl = null;
        for (MultipartFile file : files) {

            try {

                String fileName = fileService.save(file);

                imageUrl = fileService.getImageUrl(fileName);

                // do whatever you want with that
                log.info("File name: " + fileName);
                log.info("Image url: " + imageUrl);

            } catch (Exception e) {
                //  throw internal error;
                log.error("Error while saving file", e);
            }
        }

        return ResponseEntity.ok().body(imageUrl);
    }

    @PostMapping(path = "/{entityType}/{entityId}")
    public ResponseEntity<?> addToEntity(
            @PathVariable("entityType") String entityType,
            @PathVariable("entityId") Integer entityId,
            @RequestParam(name = "files") MultipartFile[] files,
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "fileType") String fileType
    ) throws IOException {
        Validate.notNull(entityType, fieldMissing("entityType"));
        Validate.notNull(entityId, fieldMissing("entityId"));
        Validate.notNull(files, fieldMissing("files"));
        Validate.notNull(fileType, fieldMissing("fileType"));

        FileDto fileDto = new FileDto(files, language, fileType, entityType, entityId);

        List<String> urls = fileService.addFileToEntity(fileDto);

        Message message;
        if (urls != null && !urls.isEmpty()) {
            message = new Message("File uploaded successfully.", IMessage.Level.SUCCESS);
        } else {
            message = new Message("File not uploaded.", IMessage.Level.ERROR);
        }

        return this.buildSuccessResponsesOfGenericType(urls, Normal.value, List.of(message), false);
    }

    @DeleteMapping(path = "/{entityType}/{entityId}")
    public ResponseEntity<?> deleteFromEntity(
            @PathVariable("entityType") String entityType,
            @PathVariable("entityId") Integer entityId,
            @RequestParam(name = "fileType") String fileType,
            @RequestParam(name = "language", required = false) String language
    ) throws IOException {
        Validate.notNull(entityType, fieldMissing("entityType"));
        Validate.notNull(entityId, fieldMissing("entityId"));
        Validate.notNull(fileType, fieldMissing("fileType"));

        FileDto fileDto = new FileDto(null, language, fileType, entityType, entityId);

        fileService.removeFileToEntity(fileDto);

        Message message = new Message("File deleted successfully.", IMessage.Level.SUCCESS);
        return this.buildSuccessResponseOfGenericType(true, Normal.value, List.of(message), false);
    }

}
