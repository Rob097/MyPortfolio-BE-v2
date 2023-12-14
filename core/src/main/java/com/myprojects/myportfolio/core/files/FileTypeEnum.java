package com.myprojects.myportfolio.core.files;

import lombok.Getter;

@Getter
public enum FileTypeEnum {
    PROFILE_IMAGE("profileImages", "%s"), // USER_ID
    CURRICULUM_VITAE("cv", "%s-%s"), // USER_ID, LANGUAGE
    COVER_IMAGE("coverImages", "%s-%s"), // ENTITY_TYPE, ENTITY_ID
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
