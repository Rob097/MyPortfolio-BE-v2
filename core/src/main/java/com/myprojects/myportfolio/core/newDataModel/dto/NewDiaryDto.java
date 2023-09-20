package com.myprojects.myportfolio.core.newDataModel.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewDiaryDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = -8754716684648310747L;

    String title;
    String description;
    Boolean isMain;
    Set<NewStoryDto> stories;

}