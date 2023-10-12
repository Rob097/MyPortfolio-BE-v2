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
public class StoryDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = -5591758414212539206L;

    @NotNull(message = "Story: Diary id cannot be null", groups = OnUpdate.class)
    Integer diaryId;
    String slug;

    @NotNull(message = "Story: Title cannot be null")
    String title;
    String description;
    LocalDate fromDate;
    LocalDate toDate;
    Boolean isPrimaryStory;
    String firstRelevantSection;
    String secondRelevantSection;

    @JsonView(Verbose.class)
    Set<Integer> projectsIds;

    @JsonView(Verbose.class)
    Set<Integer> educationsIds;

    @JsonView(Verbose.class)
    Set<Integer> experiencesIds;

    @JsonView(Verbose.class)
    Set<SkillDto> skills;

}