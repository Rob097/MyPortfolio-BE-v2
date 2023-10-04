package com.myprojects.myportfolio.core.newDataModel.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.newDataModel.dao.enums.Sex;
import com.myprojects.myportfolio.core.newDataModel.dto.skills.NewUserSkillDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewUserDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = 9184690121318354645L;

    String slug;

    @NotNull(message = "User: First name cannot be null")
    String firstName;

    @NotNull(message = "User: Last name cannot be null")
    String lastName;

    @NotNull(message = "User: Email cannot be null")
    String email;
    String phone;
    Integer age;
    AddressDto address;
    Sex sex;
    String title;
    String description;

    @Valid
    @JsonView(Verbose.class)
    Set<NewDiaryDto> diaries;

    @Valid
    @JsonView(Verbose.class)
    Set<NewProjectDto> projects;

    @Valid
    @JsonView(Verbose.class)
    Set<NewEducationDto> educations;

    @Valid
    @JsonView(Verbose.class)
    Set<NewExperienceDto> experiences;

    @JsonView(Verbose.class)
    Set<NewUserSkillDto> skills;

}