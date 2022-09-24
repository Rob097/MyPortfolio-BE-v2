package com.myprojects.myportfolio.clients.user;

import com.myprojects.myportfolio.clients.project.ProjectR;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserR {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private List<ProjectR> projects;

}
