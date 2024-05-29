package com.myprojects.myportfolio.core.dao.skills;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.myprojects.myportfolio.core.dao.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * UserSkill Is a composite relational entity that represents the many-to-many relationship between User and Skill.
 * When creating or updating, you have to indicate an already existing userId and skillId.
 * When deleting, the record is deleted by the composite key.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@IdClass(UserSkillPK.class)
@Entity
@Table(name = "user_skills")
@Cache(region = "userSkills", usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserSkill implements Serializable {

    @Serial
    private static final long serialVersionUID = -7991529850180063512L;

    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Id
    @Column(name = "skill_id", nullable = false)
    private Integer skillId;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private User user;

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Skill skill;

    @Column(name = "is_main")
    private Boolean isMain;

    @Column(name = "order_id")
    private Integer orderId;

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getId();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
        this.user = User.builder().id(userId).build();
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
        this.skillId = skill.getId();
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
        this.skill = Skill.builder().id(skillId).build();
    }

    public UserSkillPK getId() {
        return UserSkillPK.builder()
                .userId(userId)
                .skillId(skillId)
                .build();
    }

    @PrePersist
    public void completeRelationships() {
        if (this.user != null) {
            this.user.getSkills().add(this);
        }
        if (this.skill != null) {
            this.skill.getUsers().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSkill that = (UserSkill) o;

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
