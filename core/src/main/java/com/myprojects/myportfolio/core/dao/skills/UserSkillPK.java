package com.myprojects.myportfolio.core.dao.skills;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class is a composite primary key for the UserSkill entity.
 * It's composed of the userId and the skillId.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillPK implements Serializable {

    @Serial
    private static final long serialVersionUID = 3004133355114394067L;

    private Integer userId;

    private Integer skillId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSkillPK that = (UserSkillPK) o;

        if (!Objects.equals(userId, that.userId)) return false;
        return Objects.equals(skillId, that.skillId);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (skillId != null ? skillId.hashCode() : 0);
        return result;
    }
}
