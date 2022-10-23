package com.myprojects.myportfolio.core.skill;

import com.myprojects.myportfolio.core.experience.Experience;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "experience_skills")
public class ExperienceSkill implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "experience_id", nullable = false)
    private Experience experience;

    @Id
    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

}
