package com.myprojects.myportfolio.core.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.dto.skills.SkillDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoryDto extends SlugDto {

    @Serial
    private static final long serialVersionUID = -5591758414212539206L;

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class RelevantSectionDto extends BaseDto {
        @Serial
        private static final long serialVersionUID = -5591758414212539206L;

        String title;
        String description;
        Integer orderInStory;
    }

    @NotNull(message = "Story: Diary id cannot be null", groups = OnUpdate.class)
    Integer diaryId;

    @NotNull(message = "Story: Title cannot be null")
    String title;
    String description;
    LocalDate fromDate;
    LocalDate toDate;
    Set<RelevantSectionDto> relevantSections;

    Integer projectId;
    Integer orderInProject;

    Integer educationId;
    Integer orderInEducation;

    Integer experienceId;
    Integer orderInExperience;

    @JsonView(Verbose.class)
    Set<SkillDto> skills;

}