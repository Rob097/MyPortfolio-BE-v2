package com.myprojects.myportfolio.core.newDataModel.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewDiaryDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = -8754716684648310747L;

    @NotNull( message = "User Id cannot be null")
    Integer userId;
    String title;
    String description;
    Boolean isMain;

    @JsonView(Verbose.class)
    Set<NewStoryDto> stories;

}