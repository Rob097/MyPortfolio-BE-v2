package com.myprojects.myportfolio.core.experience;

import com.myprojects.myportfolio.core.education.EducationProjection;
import com.myprojects.myportfolio.core.skill.Skill;
import com.myprojects.myportfolio.core.story.Story;
import com.myprojects.myportfolio.core.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate startDate;

    // End Date (If null, still on course)
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;

    // Description
    private String description;

    @ManyToMany
    @JoinTable(
            name = "experience_skills",
            joinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private Set<Skill> skills;

    @ManyToMany(mappedBy = "experiences")
    private Set<Story> stories;

    public Experience(ExperienceProjection projection){
        super();
        this.id = projection.getId();
        this.title = projection.getTitle();
        this.employmentType = projection.getEmploymentType();
        this.companyName = projection.getCompanyName();
        this.location = projection.getLocation();
        this.startDate = projection.getStartDate();
        this.endDate = projection.getEndDate();
        this.description = projection.getDescription();
    }

}
