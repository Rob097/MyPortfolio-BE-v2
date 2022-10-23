package com.myprojects.myportfolio.core.experience;

import com.myprojects.myportfolio.core.skill.ExperienceSkill;
import com.myprojects.myportfolio.core.story.Story;
import com.myprojects.myportfolio.core.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Setter
@Getter
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

    @OneToMany(
            mappedBy = "experience",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<ExperienceSkill> skills;

    @ManyToMany(mappedBy = "experienceList")
    private Set<Story> stories;

}
