package com.myprojects.myportfolio.core.newDataModel.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.newDataModel.dao.NewProject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewStoryDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = -5591758414212539206L;

    Integer diaryId;
    String slug;
    String title;
    String description;
    LocalDate fromDate;
    LocalDate toDate;
    Boolean isPrimaryStory;
    String firstRelevantSection;
    String secondRelevantSection;

    @JsonView(Verbose.class)
    Set<Integer> projectsIds;

}