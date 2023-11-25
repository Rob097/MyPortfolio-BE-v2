package com.myprojects.myportfolio.core.files;

import com.myprojects.myportfolio.core.controllers.SimpleController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

}
