package com.myprojects.myportfolio.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto extends AuditableDto {

    private String ip;
    private String newFeatures;
    private String registerForm;

}
