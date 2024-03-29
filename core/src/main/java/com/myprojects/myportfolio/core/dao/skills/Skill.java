package com.myprojects.myportfolio.core.dao.skills;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.dao.BaseDao;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("JpaEntityListenerInspection")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "skills")
@SequenceGenerator(name = "default_gen", sequenceName = "skill_seq", allocationSize = 1)
@Cache(region = "skills", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Skill extends BaseDao {

    @Serial
    private static final long serialVersionUID = 865539761537827369L;

    private String name;

    /**
     * @Create&Update: When creating or updating a Skill, you can only specify an already existing category.
     * @Delete: When deleting a skill, the category is not deleted but the relationship is deleted.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "skill_category_fk"
            )
    )
    @JsonBackReference
    @Cache(region = "skillsCategory", usage=CacheConcurrencyStrategy.READ_ONLY)
    private SkillCategory category;

    /**
     * @Create&Update: When creating, updating a Skill, users are ignored.
     * @Delete: When deleting a skill, the relationship is deleted.
     */
    @OneToMany(
            mappedBy = "skill",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    @Cache(region = "userSkills", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<UserSkill> users = new HashSet<>();

    @PrePersist
    public void categoryCheckPersist() {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Skill category is not valid");
        }
    }

}
