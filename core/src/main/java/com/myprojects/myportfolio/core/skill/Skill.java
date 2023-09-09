package com.myprojects.myportfolio.core.skill;

import com.myprojects.myportfolio.core.user.UserSkill;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skills")
@org.hibernate.annotations.Cache(region = "skills", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Skill implements Serializable {

    @Serial
    private static final long serialVersionUID = 4898256361830275290L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(region = "userSkills", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<UserSkill> users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "skill_category_fk"
            )
    )
    @org.hibernate.annotations.Cache(region = "skillsCategory", usage=CacheConcurrencyStrategy.READ_ONLY)
    private SkillsCategory category;

}
