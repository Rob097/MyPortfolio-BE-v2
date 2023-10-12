package com.myprojects.myportfolio.core.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.dto.skills.SkillDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = -2469764529940394128L;

    @NotNull(message = "Project: User Id cannot be null", groups = OnUpdate.class)
    Integer userId;
    String slug;

    @NotNull(message = "Project: Title cannot be null")
    String title;

    String description;

    @Valid
    @JsonView(Verbose.class)
    Set<StoryDto> stories;

    @JsonView(Verbose.class)
    Set<SkillDto> skills;
}