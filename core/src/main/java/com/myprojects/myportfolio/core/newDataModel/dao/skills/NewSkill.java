package com.myprojects.myportfolio.core.newDataModel.dao.skills;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.newDataModel.dao.BaseDao;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("JpaEntityListenerInspection")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "new_skills")
public class NewSkill extends BaseDao {

    @Serial
    private static final long serialVersionUID = 865539761537827369L;

    private String name;

    // When creating or updating a Skill, you can only specify an already existing category.
    // When deleting a skill, the category is not deleted but the relationship is deleted.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "new_skill_category_fk"
            )
    )
    @JsonBackReference
    private NewSkillCategory category;

    // When creating, updating a Skill, users are ignored.
    @OneToMany(
            mappedBy = "skill",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    private Set<NewUserSkill> users = new HashSet<>();

    @PrePersist
    public void categoryCheckPersist() {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Skill category is not valid");
        }
    }

}
