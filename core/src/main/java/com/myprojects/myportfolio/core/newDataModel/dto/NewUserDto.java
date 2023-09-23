package com.myprojects.myportfolio.core.newDataModel.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewUserDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = 9184690121318354645L;

    String slug;
    String firstName;
    String lastName;
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

    public enum SexDto {
        MALE,
        FEMALE;
    }
}