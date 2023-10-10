package com.myprojects.myportfolio.core.dto.skills;

import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serial;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewSkillCategoryDto extends BaseDto {

    @Serial
    private static final long serialVersionUID = -4990702990523360375L;

    @NotNull(message = "Skill Category: id cannot be null", groups = OnUpdate.class)
    Integer id;

    @NotNull(message = "Skill Category: name cannot be null")
    String name;

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

}