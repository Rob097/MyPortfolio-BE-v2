package com.myprojects.myportfolio.core.files;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myprojects.myportfolio.core.dao.Education;
import com.myprojects.myportfolio.core.dao.Experience;
import com.myprojects.myportfolio.core.dao.Project;
import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.dto.enums.EntityTypeEnum;
import com.myprojects.myportfolio.core.repositories.EducationRepository;
import com.myprojects.myportfolio.core.repositories.ExperienceRepository;
import com.myprojects.myportfolio.core.repositories.ProjectRepository;
import com.myprojects.myportfolio.core.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class FileService implements FileServiceI {

    @Value("${google.firebase.bucketName}")
    private String bucketName;

    @Value("${google.firebase.imageUrl}")
    private String imageUrl;

    private final UserRepository userRepository;

    private final ExperienceRepository experienceRepository;

    private final ProjectRepository projectRepository;

    private final EducationRepository educationRepository;

    public FileService(UserRepository userRepository, ExperienceRepository experienceRepository, ProjectRepository projectRepository, EducationRepository educationRepository) {
        this.userRepository = userRepository;
        this.experienceRepository = experienceRepository;
        this.projectRepository = projectRepository;
        this.educationRepository = educationRepository;
    }


    /*********************/
    /** File operations **/
    /*********************/

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


    /*************************/
    /** Entities operations **/
    /*************************/

    @Override
    public List<String> addFileToEntity(FileDto fileDto) throws IOException {
        String commonLog = "addFileToEntity(" + fileDto.entityType + ", " + fileDto.entityId + ", " + fileDto.fileType + ")";
        log.info(commonLog + " - BEGIN");

        FileTypeEnum fileTypeEnum = FileTypeEnum.valueOf(fileDto.getFileType());
        EntityTypeEnum entityTypeEnum = EntityTypeEnum.valueOf(fileDto.getEntityType());
        List<String> urls = new ArrayList<>();
        List<MultipartFile> files = Arrays.stream(fileDto.getFiles()).toList();

        switch (entityTypeEnum) {
            case USER:
                User user = userRepository.findById(fileDto.getEntityId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

                switch (fileTypeEnum) {
                    case PROFILE_IMAGE:
                        String oldProfileImageUrl = user.getStringFromCustomizations(User.CustomizationsKeysEnum.PROFILE_IMAGE.getKey());
                        String newProfileImageUrl = deleteOldFileAndSaveNewFile(commonLog, oldProfileImageUrl, fileTypeEnum.getFileName(fileDto.getEntityId()), files, fileTypeEnum, urls);

                        // Update the user's customizations:
                        user.addToCustomizations(User.CustomizationsKeysEnum.PROFILE_IMAGE.getKey(), newProfileImageUrl);
                        break;
                    case CURRICULUM_VITAE:
                        JsonObject customizations = new Gson().fromJson(user.getCustomizations(), JsonObject.class);
                        boolean doesCVExist = customizations.has(User.CustomizationsKeysEnum.CURRICULUM_VITAE.getKey());
                        JsonObject cvs = doesCVExist ? customizations.getAsJsonObject(User.CustomizationsKeysEnum.CURRICULUM_VITAE.getKey()) : new JsonObject();

                        String oldCurriculumVitaeUrl = cvs.has(fileDto.getLanguage()) ? cvs.get(fileDto.getLanguage()).getAsString() : null;
                        String newCurriculumVitaeUrl = deleteOldFileAndSaveNewFile(commonLog, oldCurriculumVitaeUrl, fileTypeEnum.getFileName(fileDto.getEntityId(), fileDto.getLanguage()), files, fileTypeEnum, urls);

                        // Update the user's customizations:
                        cvs.addProperty(fileDto.getLanguage(), newCurriculumVitaeUrl);
                        customizations.add(User.CustomizationsKeysEnum.CURRICULUM_VITAE.getKey(), cvs);
                        user.setCustomizations(customizations.toString());
                        break;

                    default:
                        throw new IllegalArgumentException("File type not supported: " + fileTypeEnum);
                }

                userRepository.save(user);
                break;

            case EXPERIENCE:
                Experience experience = experienceRepository.findById(fileDto.getEntityId()).orElseThrow(() -> new EntityNotFoundException("Experience not found"));

                switch (fileTypeEnum) {
                    case COVER_IMAGE:
                        String oldCoverImageUrl = experience.getCoverImage();
                        String newCoverImageUrl = deleteOldFileAndSaveNewFile(commonLog, oldCoverImageUrl, fileTypeEnum.getFileName(fileDto.getEntityType(), fileDto.getEntityId()), files, fileTypeEnum, urls);

                        experience.setCoverImage(newCoverImageUrl);
                        break;
                    default:
                        throw new IllegalArgumentException("File type not supported: " + fileTypeEnum);
                }

                experienceRepository.save(experience);
                break;

            case PROJECT:
                Project project = projectRepository.findById(fileDto.getEntityId()).orElseThrow(() -> new EntityNotFoundException("Project not found"));

                switch (fileTypeEnum) {
                    case COVER_IMAGE:
                        String oldCoverImageUrl = project.getCoverImage();
                        String newCoverImageUrl = deleteOldFileAndSaveNewFile(commonLog, oldCoverImageUrl, fileTypeEnum.getFileName(fileDto.getEntityType(), fileDto.getEntityId()), files, fileTypeEnum, urls);

                        project.setCoverImage(newCoverImageUrl);
                        break;
                    default:
                        throw new IllegalArgumentException("File type not supported: " + fileTypeEnum);
                }

                projectRepository.save(project);
                break;

            case EDUCATION:
                Education education = educationRepository.findById(fileDto.getEntityId()).orElseThrow(() -> new EntityNotFoundException("Education not found"));

                switch (fileTypeEnum) {
                    case COVER_IMAGE:
                        String oldCoverImageUrl = education.getCoverImage();
                        String newCoverImageUrl = deleteOldFileAndSaveNewFile(commonLog, oldCoverImageUrl, fileTypeEnum.getFileName(fileDto.getEntityType(), fileDto.getEntityId()), files, fileTypeEnum, urls);

                        education.setCoverImage(newCoverImageUrl);
                        break;
                    default:
                        throw new IllegalArgumentException("File type not supported: " + fileTypeEnum);
                }

                educationRepository.save(education);
                break;

            default:
                throw new IllegalArgumentException("Entity type not supported: " + fileDto.getEntityType());
        }

        log.info(commonLog + " - URLS: " + urls);
        log.info(commonLog + " - END");
        return urls;

    }

    @Override
    public void removeFileToEntity(FileDto fileDto) throws IOException {
        String commonLog = "removeFileToEntity(" + fileDto.entityType + ", " + fileDto.entityId + ", " + fileDto.fileType + ")";
        log.info(commonLog + " - BEGIN");

        FileTypeEnum fileTypeEnum = FileTypeEnum.valueOf(fileDto.getFileType());
        EntityTypeEnum entityTypeEnum = EntityTypeEnum.valueOf(fileDto.getEntityType());

        switch (entityTypeEnum) {
            case USER:
                User user = userRepository.findById(fileDto.getEntityId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

                switch (fileTypeEnum) {
                    case PROFILE_IMAGE:
                        String oldProfileImageUrl = user.getStringFromCustomizations(User.CustomizationsKeysEnum.PROFILE_IMAGE.getKey());
                        if (oldProfileImageUrl != null) {
                            String oldProfileImageFilePath = getFileNameFromUrl(oldProfileImageUrl);
                            log.info(commonLog + " - Deleting old file: " + oldProfileImageFilePath);
                            delete(oldProfileImageFilePath);

                            // Update the user's customizations:
                            user.addToCustomizations(User.CustomizationsKeysEnum.PROFILE_IMAGE.getKey(), null);
                        }

                        break;
                    case CURRICULUM_VITAE:
                        JsonObject customizations = new Gson().fromJson(user.getCustomizations(), JsonObject.class);
                        boolean doesCVExist = customizations.has(User.CustomizationsKeysEnum.CURRICULUM_VITAE.getKey());
                        JsonObject cvs = doesCVExist ? customizations.getAsJsonObject(User.CustomizationsKeysEnum.CURRICULUM_VITAE.getKey()) : new JsonObject();

                        String oldCurriculumVitaeUrl = cvs.has(fileDto.getLanguage()) ? cvs.get(fileDto.getLanguage()).getAsString() : null;
                        if (oldCurriculumVitaeUrl != null) {
                            String oldCurriculumVitaeFilePath = getFileNameFromUrl(oldCurriculumVitaeUrl);
                            log.info(commonLog + " - Deleting old file: " + oldCurriculumVitaeFilePath);
                            delete(oldCurriculumVitaeFilePath);

                            // Update the user's customizations:
                            cvs.remove(fileDto.getLanguage());
                            customizations.add(User.CustomizationsKeysEnum.CURRICULUM_VITAE.getKey(), cvs);
                            user.setCustomizations(customizations.toString());
                        }

                        break;
                    default:
                        throw new IllegalArgumentException("File type not supported: " + fileTypeEnum);
                }

                userRepository.save(user);
                break;

            default:
                throw new IllegalArgumentException("Entity type not supported: " + fileDto.getEntityType());

        }

        log.info(commonLog + " - END");
    }

    private String deleteOldFileAndSaveNewFile(String commonLog, String oldImageUrl, String
            initialFileName, List<MultipartFile> files, FileTypeEnum fileTypeEnum, List<String> urls) throws
            IOException {
        if (oldImageUrl != null) {
            String oldImageFilePath = getFileNameFromUrl(oldImageUrl);
            log.info(commonLog + " - Deleting old file: " + oldImageFilePath);
            delete(oldImageFilePath);
        }

        // In this case we manage only one file.
        String fileName = getCompleteFileName(fileTypeEnum.getFolderName(), initialFileName, files.get(0).getOriginalFilename());
        String url = getImageUrl(save(fileName, files.get(0)));
        urls.add(url);
        return url;
    }

}
