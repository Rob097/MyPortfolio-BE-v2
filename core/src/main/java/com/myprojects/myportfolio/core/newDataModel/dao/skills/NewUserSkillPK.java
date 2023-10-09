package com.myprojects.myportfolio.core.newDataModel.dao.skills;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is a composite primary key for the NewUserSkill entity.
 * It's composed of the userId and the skillId.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserSkillPK implements Serializable {

    @Serial
    private static final long serialVersionUID = 3004133355114394067L;

    private Integer userId;

    private Integer skillId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewUserSkillPK that = (NewUserSkillPK) o;

        if (!userId.equals(that.userId)) return false;
        return skillId.equals(that.skillId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + skillId.hashCode();
        return result;
    }
}