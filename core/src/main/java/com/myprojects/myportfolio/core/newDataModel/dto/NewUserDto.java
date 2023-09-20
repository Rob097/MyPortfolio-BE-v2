package com.myprojects.myportfolio.core.newDataModel.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

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
    String nationality;
    String nation;
    String province;
    String city;
    String cap;
    String address;
    SexDto sex;
    String title;
    String description;

    public enum SexDto {
        MALE,
        FEMALE;
    }
}