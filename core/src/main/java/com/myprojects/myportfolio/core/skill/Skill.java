package com.myprojects.myportfolio.core.skill;

import com.myprojects.myportfolio.core.story.Story;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "skillList")
    private Set<Story> stories;

    /*@OneToMany(
            mappedBy = "skill",
            orphanRemoval = true,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<UserSkill> userSkills;

    @OneToMany(
            mappedBy = "skill",
            orphanRemoval = true,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<ExperienceSkill> experienceSkills;*/

}
