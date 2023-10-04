package com.myprojects.myportfolio.core.newDataModel.dao.skills;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.newDataModel.dao.BaseDao;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "new_skills_category")
public class NewSkillCategory extends BaseDao {

    @Serial
    private static final long serialVersionUID = 1506623878371112144L;

    private String name;

    // Skill Category can't create, update or delete skills.
    // The bidirectional relationship is used only for reading skills internally in the BE.
    @OneToMany(
            mappedBy = "category",
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    private Set<NewSkill> skills = new HashSet<>();

}
