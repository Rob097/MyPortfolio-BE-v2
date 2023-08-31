package com.myprojects.myportfolio.core.experience;

import com.myprojects.myportfolio.core.skill.Skill;
import com.myprojects.myportfolio.core.story.Story;
import com.myprojects.myportfolio.core.user.User;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "experiences")
@org.hibernate.annotations.Cache(region = "experiences", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Experience implements Serializable {

    private static final long serialVersionUID = 21708545405311913L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_experience_fk"
            )
    )
    @org.hibernate.annotations.Cache(region = "users", usage=CacheConcurrencyStrategy.READ_ONLY)
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
    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate startDate;

    // End Date (If null, still on course)
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;

    // Description
    @Column(nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "experience_skills",
            joinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    @org.hibernate.annotations.Cache(region = "skills", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Skill> skills;

    @ManyToMany(
            mappedBy = "experiences",
            fetch = FetchType.LAZY
    )
    @org.hibernate.annotations.Cache(region = "stories", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Story> stories;

}
