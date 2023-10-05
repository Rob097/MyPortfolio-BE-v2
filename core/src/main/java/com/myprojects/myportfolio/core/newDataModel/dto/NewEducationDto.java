package com.myprojects.myportfolio.core.newDataModel.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.newDataModel.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.newDataModel.dto.skills.NewSkillDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewEducationDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = -1216538670421106118L;

    @NotNull(message = "Education: User Id cannot be null", groups = OnUpdate.class)
    Integer userId;

    String slug;

    @NotNull(message = "Education: Field cannot be null")
    String field;

    @NotNull(message = "Education: School cannot be null")
    String school;

    @NotNull(message = "Education: Degree cannot be null")
    String degree;

    Double grade;

    @NotNull(message = "Education: Description cannot be null")
    String description;

    LocalDate fromDate;
    LocalDate toDate;

    @Valid
    @JsonView(Verbose.class)
    Set<NewStoryDto> stories;

    @JsonView(Verbose.class)
    Set<NewSkillDto> skills;
}
