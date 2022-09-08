package com.myprojects.myportfolio.clients.project;

import com.myprojects.myportfolio.clients.user.UserR;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectR {

    private Integer id;
    private String name;
    private UserR owner;

}
