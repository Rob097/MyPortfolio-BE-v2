package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.Attachment;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface AttachmentServiceI extends BaseServiceI<Attachment> {

    String getImageUrl(String name);

    String getFileNameFromUrl(String url);

    Attachment save(MultipartFile file) throws IOException;

    Attachment save(String folder, MultipartFile file) throws IOException;

    void deleteByUrl(String url);

    void deleteById(Integer id);

    void deleteByUserId(Integer userId);

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
