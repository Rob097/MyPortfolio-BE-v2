package com.myprojects.myportfolio.core.experience;

import com.myprojects.myportfolio.core.skill.UserSkill;
import com.myprojects.myportfolio.core.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_experience_fk"
            )
    )
    private User user;

    // Title (Ex: Full Stack Software Engineer)
    private String title;

    @Enumerated(EnumType.STRING)
    private EmploymentTypeEnum employmentType;

    // Company Name (Ex: Microsoft)
    private String companyName;

    // Location (Ex: London, United Kingdom)
    private String location;

    // Start Date
    @Column(nullable = false)
    private Calendar startDate;

    // End Date (If null, still on course)
    private Calendar endDate;

    // Description
    private String description;

    @ManyToMany(mappedBy = "experiences", fetch = FetchType.LAZY)
    private Set<UserSkill> skills;

}
