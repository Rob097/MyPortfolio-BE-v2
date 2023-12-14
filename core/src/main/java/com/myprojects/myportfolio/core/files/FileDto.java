package com.myprojects.myportfolio.core.files;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    MultipartFile[] files;
    String language;
    String fileType;
    String entityType;
    Integer entityId;
}
