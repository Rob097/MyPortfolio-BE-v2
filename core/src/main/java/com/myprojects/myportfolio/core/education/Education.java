package com.myprojects.myportfolio.core.education;

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
@Table(name = "educations")
public class Education {

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
                    name = "user_education_fk"
            )
    )
    private User user;

    // School or University
    @Column(nullable = false)
    private String school;

    // Title of study
    @Column(nullable = false)
    private String degree;

    // Field of Study
    private String field;

    // Start Date
    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate startDate;

    // End Date (If null, still on course)
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;

    // Grade
    private Double grade;

    // Description
    private String description;

    @ManyToMany(mappedBy = "educations")
    private Set<Story> stories;

    public Education(EducationProjection projection){
        super();
        this.id = projection.getId();
        this.school = projection.getSchool();
        this.degree = projection.getDegree();
        this.field = projection.getField();
        this.startDate = projection.getStartDate();
        this.endDate = projection.getEndDate();
        this.grade = projection.getGrade();
        this.description = projection.getDescription();
    }

}