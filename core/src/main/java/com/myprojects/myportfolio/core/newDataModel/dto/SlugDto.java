package com.myprojects.myportfolio.core.newDataModel.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SlugDto extends AuditableDto {
    String slug;
}