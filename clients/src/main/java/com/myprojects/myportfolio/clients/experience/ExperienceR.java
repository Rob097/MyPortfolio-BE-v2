package com.myprojects.myportfolio.clients.experience;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExperienceR {

    private Integer id;
    private Integer userId;
    private String title;
    private String employmentType;
    private String companyName;
    private String location;
    private Instant startDate;
    private Instant endDate;
    private String description;

}
