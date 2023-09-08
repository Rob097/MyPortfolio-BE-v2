package com.myprojects.myportfolio.core.skill;

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
@Table(name = "skills_category")
@org.hibernate.annotations.Cache(region = "skillsCategory", usage = CacheConcurrencyStrategy.READ_WRITE)
public class SkillsCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 8726958352000195339L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    private String name;

    @OneToMany(
            mappedBy = "category",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    @org.hibernate.annotations.Cache(region = "skills", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Skill> skills;

}
