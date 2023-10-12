package com.myprojects.myportfolio.core.dto.skills;

import com.myprojects.myportfolio.core.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SkillDto extends BaseDto {

    @Serial
    private static final long serialVersionUID = -908019145564415308L;

    @NotNull(message = "Skill: name cannot be null")
    String name;

    @Valid
    @NotNull(message = "Skill: category cannot be null")
    SkillCategoryDto category;

}