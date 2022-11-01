package com.myprojects.myportfolio.clients.education;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationQ {

    private Integer id;
    private Integer userId;
    private LocalDate referenceDate;
}
