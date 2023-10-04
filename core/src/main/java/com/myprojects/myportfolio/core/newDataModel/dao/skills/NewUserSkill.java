package com.myprojects.myportfolio.core.newDataModel.dao.skills;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.myprojects.myportfolio.core.newDataModel.dao.BaseDao;
import com.myprojects.myportfolio.core.newDataModel.dao.NewUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serial;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "new_user_skills")
public class NewUserSkill extends BaseDao {

    @Serial
    private static final long serialVersionUID = -7991529850180063512L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private NewUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    @JsonBackReference
    private NewSkill skill;

    @Column(name = "is_main")
    private boolean isMain;

    @Column(name = "order_id")
    private Integer orderId;

}
