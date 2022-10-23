package com.myprojects.myportfolio.core.education;

import com.myprojects.myportfolio.core.story.Story;
import com.myprojects.myportfolio.core.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
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
    @Column(nullable = false)
    private Calendar startDate;

    // End Date (If null, still on course)
    private Calendar endDate;

    // Grade
    private Double grade;

    // Description
    private String description;

    @ManyToMany(mappedBy = "educationList")
    private Set<Story> stories;

}
