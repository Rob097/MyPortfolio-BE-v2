package com.myprojects.myportfolio.core.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.myprojects.myportfolio.core.configAndUtils.UtilsServiceI;
import com.myprojects.myportfolio.core.dao.Attachment;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.repositories.AttachmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class AttachmentService extends BaseService<Attachment> implements AttachmentServiceI {

    private final AttachmentRepository attachmentRepository;

    private final UtilsServiceI utilsService;

    @Value("${google.firebase.bucketName}")
    private String bucketName;

    @Value("${google.firebase.imageUrl}")
    private String imageUrl;

    public AttachmentService(AttachmentRepository attachmentRepository, UtilsServiceI utilsService) {
        super();
        this.repository = attachmentRepository;

        this.attachmentRepository = attachmentRepository;
        this.utilsService = utilsService;
    }

    /***************************/
    /** Attachment operations **/
    /***************************/

    @EventListener
    public void init(ApplicationReadyEvent event) {
        log.info("Initializing Firebase...");
        try {

            ClassPathResource serviceAccount = new ClassPathResource("serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(bucketName)
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (Exception ex) {
            log.error("Error initializing Firebase: " + ex.getMessage(), ex);
        }

        log.info("Firebase initialized successfully.");
    }

    @Override
    public String getImageUrl(String name) {
        return String.format(imageUrl, name.replace("/", "%2F"));
    }

    @Override
    public String getFileNameFromUrl(String url) {
        return url.substring(imageUrl.indexOf("%s"), url.indexOf("?alt=media")).replace("%2F", "/");
    }

    @Override
    public Attachment save(MultipartFile file) throws IOException {
        return save(null, file);
    }

    @Override
    public Attachment save(String name, MultipartFile file) throws IOException {
        User currentLoggedInUser = utilsService.getCurrentLoggedInUser();
        if (currentLoggedInUser == null) {
            throw new RuntimeException("User not found.");
        }

        Bucket bucket = StorageClient.getInstance().bucket();

        if (StringUtils.isBlank(name)) {
            if (file.getOriginalFilename() == null) {
                throw new RuntimeException("File name is required.");
            }
            String folderName = (utilsService.isProd() ? "" : "TEST_" ) + "USER_" + currentLoggedInUser.getId();
            name = folderName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
        }

        bucket.create(name, file.getBytes(), file.getContentType());

        String url = getImageUrl(name);
        Attachment attachment = new Attachment();
        attachment.setType(file.getContentType());
        attachment.setUrl(url);
        attachment.setUser(currentLoggedInUser);

        return super.save(attachment);
    }

    @Override
    public void deleteByUrl(String url) {
        attachmentRepository.findByUrl(url).ifPresent(attachment -> {
            User currentLoggedInUser = utilsService.getCurrentLoggedInUser();
            if (currentLoggedInUser == null || !attachment.getUserId().equals(currentLoggedInUser.getId())) {
                throw new RuntimeException("User not found or not authorized to delete this attachment.");
            }

            deleteOnFirebase(attachment.getUrl());
            attachmentRepository.delete(attachment);
        });
    }

    @Override
    public void deleteById(Integer id) {
        attachmentRepository.findById(id).ifPresent(attachment -> {
            User currentLoggedInUser = utilsService.getCurrentLoggedInUser();
            if (currentLoggedInUser == null || !attachment.getUserId().equals(currentLoggedInUser.getId())) {
                throw new RuntimeException("User not found or not authorized to delete this attachment.");
            }

            deleteOnFirebase(attachment.getUrl());
            attachmentRepository.delete(attachment);
        });
    }

    @Override
    public void deleteByUserId(Integer userId) {
        User currentLoggedInUser = utilsService.getCurrentLoggedInUser();
        if (currentLoggedInUser == null || !userId.equals(currentLoggedInUser.getId())) {
            throw new RuntimeException("User not found or not authorized to delete this attachment.");
        }

        List<Attachment> allByUserId = attachmentRepository.findAllByUserId(userId);
        if (allByUserId != null && !allByUserId.isEmpty()) {
            allByUserId.forEach(attachment -> {
                try {
                    deleteOnFirebase(attachment.getUrl());
                    attachmentRepository.delete(attachment);
                } catch (Exception e) {
                    log.error("Error deleting attachment: " + e.getMessage(), e);
                }
            });
        }
    }

    private void deleteOnFirebase(String url) {
        Bucket bucket = StorageClient.getInstance().bucket();
        String filePath = getFileNameFromUrl(url);
        bucket.getStorage().delete(bucket.getName(), filePath);
    }

}
