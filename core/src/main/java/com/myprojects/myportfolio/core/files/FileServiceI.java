package com.myprojects.myportfolio.core.files;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileServiceI {

    String getImageUrl(String name);

    // getFileNameFromUrl
    String getFileNameFromUrl(String url);

    String save(MultipartFile file) throws IOException;

    String save(String folder, MultipartFile file) throws IOException;

    void delete(String path);

    default String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    default String generateFileName(String originalFileName) {
        return UUID.randomUUID() + getExtension(originalFileName);
    }

    default String getCompleteFileName(String folder, String fileName, String originalFileName) {
        return folder + "/" + fileName + "." + getExtension(originalFileName);
    }
}
