package com.myprojects.myportfolio.core.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DiaryDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = -8754716684648310747L;

    @NotNull(message = "Diary: User Id cannot be null", groups = OnUpdate.class)
    Integer userId;
    String title;
    String description;
    Boolean isMain;
    Integer mainStoryId;

    @Valid
    @JsonView(Verbose.class)
    Set<StoryDto> stories;

}