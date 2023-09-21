package com.myprojects.myportfolio.core.newDataModel.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewProjectDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = -2469764529940394128L;

    Integer userId;
    String slug;
    String title;
    String description;

    @JsonView(Verbose.class)
    Set<NewStoryDto> stories;
}