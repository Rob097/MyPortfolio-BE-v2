package com.myprojects.myportfolio.clients.education;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EducationR {

    private Integer id;
    private Integer userId;
    private String school;
    private String degree;
    private String field;
    private Instant startDate;
    private Instant endDate;
    private Double grade;
    private String description;
    private Set<Integer> storiesId;

}
