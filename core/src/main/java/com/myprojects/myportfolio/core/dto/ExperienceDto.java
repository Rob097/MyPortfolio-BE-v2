package com.myprojects.myportfolio.core.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.dao.enums.EmploymentTypeEnum;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.dto.skills.SkillDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExperienceDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = -2352783680197655762L;

    @NotNull(message = "Education: User Id cannot be null", groups = OnUpdate.class)
    Integer userId;

    String slug;

    @NotNull(message = "Experience: Title cannot be null")
    String title;

    EmploymentTypeEnum employmentType;

    String companyName;

    String location;

    @NotNull(message = "Experience: Description cannot be null")
    String description;

    LocalDate fromDate;
    LocalDate toDate;

    Integer mainStoryId;

    @Valid
    @JsonView(Verbose.class)
    Set<StoryDto> stories;

    @JsonView(Verbose.class)
    Set<SkillDto> skills;

}
