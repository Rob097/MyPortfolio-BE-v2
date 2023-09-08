package com.myprojects.myportfolio.core.story;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.education.Education;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.project.Project;
import com.myprojects.myportfolio.core.skill.Skill;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stories")
@org.hibernate.annotations.Cache(region = "stories", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Story implements Serializable {

    private static final long serialVersionUID = -6674119427069707283L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime entryDateTime;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "DATE")
    private LocalDate fromDate;

    @Column(columnDefinition = "DATE")
    private LocalDate toDate;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPrimaryStory;

    @Column(columnDefinition = "TEXT")
    private String firstRelevantSection;

    @Column(columnDefinition = "TEXT")
    private String secondRelevantSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "diary_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "diary_story_fk"
            )
    )
    @org.hibernate.annotations.Cache(region = "diaries", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Diary diary;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "story_educations",
            joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "education_id", referencedColumnName = "id"))
    @org.hibernate.annotations.Cache(region = "educations", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Education> educations;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "story_experiences",
            joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"))
    @org.hibernate.annotations.Cache(region = "experiences", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Experience> experiences;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "story_skills",
            joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    @org.hibernate.annotations.Cache(region = "skills", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Skill> skills;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "story_projects",
            joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    @org.hibernate.annotations.Cache(region = "projects", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Project> projects;

}
