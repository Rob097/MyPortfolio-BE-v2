package com.myprojects.myportfolio.core.newDataModel.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @NotNull( message = "First name cannot be null")
    String firstName;

    @NotNull( message = "Last name cannot be null")
    String lastName;

    @NotNull( message = "Email cannot be null")
    String email;
    String phone;
    Integer age;
    AddressDto address;
    SexDto sex;
    String title;
    String description;

    @JsonView(Verbose.class)
    Set<NewDiaryDto> diaries;

    @JsonView(Verbose.class)
    Set<NewProjectDto> projects;

    @JsonView(Verbose.class)
    Set<NewEducationDto> educations;

    public enum SexDto {
        MALE,
        FEMALE;
    }
}