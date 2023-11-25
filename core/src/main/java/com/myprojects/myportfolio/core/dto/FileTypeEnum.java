package com.myprojects.myportfolio.core.dto;

import lombok.Getter;

@Getter
public enum FileTypeEnum {
    PROFILE_IMAGE("profileImages", "%s"),
    CURRICULUM_VITAE("cv", "%s-%s"),
    ;

    private final String folderName;
    private final String fileName;

    FileTypeEnum(String folderName, String fileName) {
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public String getFileName(Object... args) {
        return String.format(fileName, args);
    }
}
