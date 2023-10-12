package com.myprojects.myportfolio.core.dao.skills;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.dao.BaseDao;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "skills_category")
public class SkillCategory extends BaseDao {

    @Serial
    private static final long serialVersionUID = 1506623878371112144L;

    private String name;

    /**
     * The bidirectional relationship is used only for reading skills internally in the BE.
     * The Skill Category can't create, update or delete skills.
     */
    @OneToMany(
            mappedBy = "category",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();

}
