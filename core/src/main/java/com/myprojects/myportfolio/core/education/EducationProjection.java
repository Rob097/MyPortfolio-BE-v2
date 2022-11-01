package com.myprojects.myportfolio.core.education;

import java.time.LocalDate;

public interface EducationProjection {
    Integer getId();
    String getSchool();
    String getDegree();
    String getField();
    LocalDate getStartDate();
    LocalDate getEndDate();
    Double getGrade();
    String getDescription();
}
