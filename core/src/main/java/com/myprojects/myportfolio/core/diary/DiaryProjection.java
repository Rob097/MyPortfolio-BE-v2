package com.myprojects.myportfolio.core.diary;

import java.time.LocalDateTime;

public interface DiaryProjection {
    Integer getId();
    LocalDateTime getEntryDateTime();
}
