package com.myprojects.myportfolio.core.story;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface StoryProjection {
    Integer getId();
    LocalDateTime getEntryDateTime();
    String getTitle();
    String getDescription();
    LocalDate getFromDate();
    LocalDate getToDate();
    Boolean getIsPrimaryStory();
}
