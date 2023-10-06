package com.myprojects.myportfolio.core.newDataModel.dao.skills;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * NewUserSkill Is a composite relational entity that represents the many-to-many relationship between NewUser and NewSkill.
 * When creating or updating, you have to indicate an already existing userId and skillId.
 * When deleting, the record is deleted by the composite key.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@IdClass(NewUserSkillPK.class)
@Entity
@Table(name = "new_user_skills")
public class NewUserSkill implements Serializable {

    @Serial
    private static final long serialVersionUID = -7991529850180063512L;

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "skill_id")
    private Integer skillId;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private NewUser user;

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private NewSkill skill;

    @Column(name = "is_main")
    private Boolean isMain;

    @Column(name = "order_id")
    private Integer orderId;

    public void setUser(NewUser user) {
        this.user = user;
        this.userId = user.getId();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
        this.user = NewUser.builder().id(userId).build();
    }

    public void setSkill(NewSkill skill) {
        this.skill = skill;
        this.skillId = skill.getId();
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
        this.skill = NewSkill.builder().id(skillId).build();
    }

    public NewUserSkillPK getId() {
        return NewUserSkillPK.builder()
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

    @PreRemove
    public void removeRelationships() {
        if (this.user != null) {
            this.user.getSkills().remove(this);
        }
        if (this.skill != null) {
            this.skill.getUsers().remove(this);
        }
    }

}
