package com.myprojects.myportfolio.core.experience;

import java.time.LocalDate;

public interface ExperienceProjection {
    Integer getId();
    String getTitle();
    EmploymentTypeEnum getEmploymentType();
    String getCompanyName();
    String getLocation();
    LocalDate getStartDate();
    LocalDate getEndDate();
    String getDescription();
}
