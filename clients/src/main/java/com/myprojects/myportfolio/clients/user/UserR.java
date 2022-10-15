package com.myprojects.myportfolio.clients.user;

import com.myprojects.myportfolio.clients.project.ProjectR;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserR {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String nationality;
    private String nation;
    private String province;
    private String city;
    private String cap;
    private String address;
    private String sex;
    private List<ProjectR> projects;

}
