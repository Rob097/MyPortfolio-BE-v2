package com.myprojects.myportfolio.core.newDataModel.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serial;

@Value
@Builder
@EqualsAndHashCode(callSuper = true)
public class NewDiaryDto extends AuditableDto {

    @Serial
    private static final long serialVersionUID = -8754716684648310747L;

}