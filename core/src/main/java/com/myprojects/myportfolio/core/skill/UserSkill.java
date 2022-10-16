package com.myprojects.myportfolio.core.skill;

import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_skills", uniqueConstraints = @UniqueConstraint(columnNames={"user_id", "skill_id"}))
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @ManyToMany
    @JoinTable(
            name = "user_skill_experiences",
            joinColumns = @JoinColumn(name = "user_skill_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"))
    private Set<Experience> experiences;

}
