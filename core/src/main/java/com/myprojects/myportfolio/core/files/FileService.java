package com.myprojects.myportfolio.core.files;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class FileService implements FileServiceI {

    @Value("${google.firebase.bucketName}")
    private String bucketName;

    @Value("${google.firebase.imageUrl}")
    private String imageUrl;

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
    public String save(MultipartFile file) throws IOException {
        return save(null, file);
    }

    @Override
    public String save(String name, MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        if (StringUtils.isBlank(name)) {
            name = generateFileName(file.getOriginalFilename());
        }

        bucket.create(name, file.getBytes(), file.getContentType());

        return name;
    }

    @Override
    public void delete(String path) {
        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.getStorage().delete(bucket.getName(), path);
    }

}
