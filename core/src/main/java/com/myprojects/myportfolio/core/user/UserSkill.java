package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.core.skill.Skill;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_skills")
@org.hibernate.annotations.Cache(region = "userSkills", usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserSkill implements Serializable {

    @Serial
    private static final long serialVersionUID = 1290524874404709150L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "is_main")
    private boolean isMain;

    @Column(name = "order_id")
    private Integer orderId;

}
