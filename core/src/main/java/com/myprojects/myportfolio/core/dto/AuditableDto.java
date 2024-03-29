package com.myprojects.myportfolio.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditableDto extends BaseDto {
    Timestamp createdAt;
    Timestamp updatedAt;
}