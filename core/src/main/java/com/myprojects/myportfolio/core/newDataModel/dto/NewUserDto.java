package com.myprojects.myportfolio.core.newDataModel.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;

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
    List<NewDiaryDto> diaries;

    public enum SexDto {
        MALE,
        FEMALE;
    }
}