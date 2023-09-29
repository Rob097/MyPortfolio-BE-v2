package com.myprojects.myportfolio.core.newDataModel.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @NotNull( message = "User Id cannot be null")
    Integer userId;

    String slug;

    @NotNull( message = "Field cannot be null")
    String field;

    @NotNull( message = "School cannot be null")
    String school;

    @NotNull( message = "Degree cannot be null")
    String degree;

    Double grade;

    @NotNull( message = "Description cannot be null")
    String description;

    LocalDate fromDate;
    LocalDate toDate;

    @JsonView(Verbose.class)
    Set<NewStoryDto> stories;
}
