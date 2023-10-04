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

}
