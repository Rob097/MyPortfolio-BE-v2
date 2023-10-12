package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.myprojects.myportfolio.core.aspects.interfaces.SlugSource;
import com.myprojects.myportfolio.core.dao.skills.Skill;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.io.Serial;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "stories", uniqueConstraints = {@UniqueConstraint(columnNames = {"diary_id", "slug"})})
public class Story extends SlugDao {

    @Serial
    private static final long serialVersionUID = 3219080188292925051L;

    @Getter
    @AllArgsConstructor
    public enum FIELDS {
        ID("id"),
        CREATEDAT("createdAt"),
        UPDATEDAT("updatedAt"),
        DIARYID("diaryId"),
        SLUG("slug"),
        TITLE("title"),
        DESCRIPTION("description"),
        FROMDATE("fromDate"),
        TODATE("toDate"),
        ISPRIMARYSTORY("isPrimaryStory"),
        FIRSTRELEVANTSECTION("firstRelevantSection"),
        SECONDRELEVANTSECTION("secondRelevantSection");

        private final String name;
    }

    @SlugSource
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

    /**
     * @Create: When creating a story, we have to specify an already existing diaryId
     * @Update: When updating a story, we can also update the diaryId
     * @Delete: When deleting a story, the relation with the diary is deleted
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "diary_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "diary_story_fk"
            )
    )
    @JsonBackReference
    @Builder.Default
    private Diary diary = new Diary();

    /**
     * @Owner: Project is the owner of the relationship.
     * @Create: When creating a story, we have to specify an already existing projectId
     * @Update: When updating a story, nothing happens to the relationship (no add, no delete)
     * @Delete: When deleting a story, the relation with the project is deleted
     */
    @ManyToMany(mappedBy = "stories", fetch = FetchType.LAZY)
    @JsonBackReference
    @Builder.Default
    private Set<Project> projects = new HashSet<>();

    /**
     * @Owner: Education is the owner of the relationship.
     * @Create: When creating a story, we have to specify an already existing educationId
     * @Update: When updating a story, nothing happens to the relationship (no add, no delete)
     * @Delete: When deleting a story, the relation with the education is deleted
     */
    @ManyToMany(mappedBy = "stories", fetch = FetchType.LAZY)
    @JsonBackReference
    @Builder.Default
    private Set<Education> educations = new HashSet<>();

    /**
     * @Owner: Experience is the owner of the relationship.
     * @Create: When creating a story, we have to specify an already existing experienceId
     * @Update: When updating a story, nothing happens to the relationship (no add, no delete)
     * @Delete: When deleting a story, the relation with the experience is deleted
     */
    @ManyToMany(mappedBy = "stories", fetch = FetchType.LAZY)
    @JsonBackReference
    @Builder.Default
    private Set<Experience> experiences = new HashSet<>();

    /**
     * @Create: When creating a story, we can specify a list of ALREADY EXISTING skills
     * @Update: When updating a story, we can also update the skills.
     *          - If a skill is already in the list, nothing happens
     *          - If a skill is not in the list, it is added
     *          - If a skill is in the list but not in the new list, it is removed (ATTENTION)
     * @Delete: When deleting a story, the relationship is removed
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "story_skills",
            joinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();

    @Override
    public void completeRelationships() {
        this.getProjects().forEach(project ->
                project.getStories().add(this)
        );
        this.getEducations().forEach(education ->
                education.getStories().add(this)
        );
        this.getExperiences().forEach(experience ->
                experience.getStories().add(this)
        );
        if (this.getDiary() != null) {
            if (this.getDiary().getStories() == null)
                this.getDiary().setStories(new HashSet<>());
            this.getDiary().getStories().add(this);
        }
    }

    @Override
    public void removeRelationships() {
        this.getProjects().forEach(project ->
                project.getStories().remove(this)
        );
        this.getEducations().forEach(education ->
                education.getStories().remove(this)
        );
        this.getExperiences().forEach(experience ->
                experience.getStories().remove(this)
        );
        if (this.getDiary() != null) {
            this.getDiary().getStories().remove(this);
        }
    }

    //////////////////////
    //  Custom methods  //
    //////////////////////

    public Integer getDiaryId() {
        if (this.getDiary() == null)
            return null;
        return this.getDiary().getId();
    }
}
