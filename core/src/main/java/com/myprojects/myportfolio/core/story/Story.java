package com.myprojects.myportfolio.core.story;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.education.Education;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.skill.Skill;
import lombok.*;

import javax.persistence.*;
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
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime entryDateTime;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(columnDefinition = "DATE")
    private LocalDate fromDate;

    @Column(columnDefinition = "DATE")
    private LocalDate toDate;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPrimaryStory;

    @ManyToOne
    @JoinColumn(
            name = "diary_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "diary_story_fk"
            )
    )
    private Diary diary;

    @ManyToMany
    @JoinTable(
            name = "story_educations",
            joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "education_id", referencedColumnName = "id"))
    private Set<Education> educations;

    @ManyToMany
    @JoinTable(
            name = "story_experiences",
            joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"))
    private Set<Experience> experiences;

    @ManyToMany
    @JoinTable(
            name = "story_skills",
            joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private Set<Skill> skills;

    public Story(StoryProjection projection) {
        this.id = projection.getId();
        this.entryDateTime = projection.getEntryDateTime();
        this.title = projection.getTitle();
        this.description = projection.getDescription();
        this.fromDate = projection.getFromDate();
        this.toDate = projection.getToDate();
        this.isPrimaryStory = projection.getIsPrimaryStory();
    }
}
