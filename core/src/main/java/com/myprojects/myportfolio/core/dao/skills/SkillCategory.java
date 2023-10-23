package com.myprojects.myportfolio.core.dao.skills;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.dao.BaseDao;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@SequenceGenerator(name = "skill_category_gen", sequenceName = "skill_category_seq", allocationSize = 1)
@Cache(region = "skillsCategory", usage = CacheConcurrencyStrategy.READ_WRITE)
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
    @Cache(region = "skills", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Skill> skills = new HashSet<>();

}
