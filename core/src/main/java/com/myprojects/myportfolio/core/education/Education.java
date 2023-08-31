package com.myprojects.myportfolio.core.education;

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
@Table(name = "educations")
@org.hibernate.annotations.Cache(region = "educations", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Education implements Serializable {

    private static final long serialVersionUID = 8000113888384130753L;

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
                    name = "user_education_fk"
            )
    )
    @org.hibernate.annotations.Cache(region = "users", usage=CacheConcurrencyStrategy.READ_ONLY)
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
    @Column(nullable = false)
    private String description;

    @ManyToMany(
            mappedBy = "educations",
            fetch = FetchType.LAZY
    )
    @org.hibernate.annotations.Cache(region = "stories", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Story> stories;

}