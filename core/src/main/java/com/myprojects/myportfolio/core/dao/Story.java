package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.myprojects.myportfolio.core.aspects.interfaces.SlugSource;
import com.myprojects.myportfolio.core.dao.skills.Skill;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@SequenceGenerator(name = "story_gen", sequenceName = "story_seq", allocationSize = 1)
@Cache(region = "stories", usage = CacheConcurrencyStrategy.READ_WRITE)
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
    @Cache(region = "diaries", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Diary diary = new Diary();

    /**
     * @Owner: Project is the owner of the relationship.
     * @Create: When creating a story, we have to specify an already existing projectId
     * @Update: When updating a story, we have to specify an already existing projectId
     *          If we don't specify a projectId, the relationship is removed
     * @Delete: When deleting a story, the relation with the project is deleted
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "project_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "project_story_fk"
            )
    )
    @JsonBackReference
    @Cache(region = "projects", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Project project;

    // Defines the order of the story in the project
    private Integer orderInProject;

    /**
     * @Owner: Education is the owner of the relationship.
     * @Create: When creating a story, we have to specify an already existing educationId
     * @Update: When updating a story, we have to specify an already existing educationId
     *          If we don't specify a educationId, the relationship is removed
     * @Delete: When deleting a story, the relation with the education is deleted
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "education_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "education_story_fk"
            )
    )
    @JsonBackReference
    @Cache(region = "educations", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Education education;

    // Defines the order of the story in the education
    private Integer orderInEducation;

    /**
     * @Owner: Experience is the owner of the relationship.
     * @Create: When creating a story, we have to specify an already existing experienceId
     * @Update: When updating a story, we have to specify an already existing experienceId
     *          If we don't specify a experienceId, the relationship is removed
     * @Delete: When deleting a story, the relation with the experience is deleted
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "experience_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "experience_story_fk"
            )
    )
    @JsonBackReference
    @Cache(region = "experiences", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Experience experience;

    // Defines the order of the story in the experience
    private Integer orderInExperience;

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
    @Cache(region = "skills", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<Skill> skills = new HashSet<>();

    @Override
    public void completeRelationships() {
        if (this.getDiary() != null) {
            if (this.getDiary().getStories() == null)
                this.getDiary().setStories(new HashSet<>());
            this.getDiary().getStories().add(this);
        }
        if (this.getProject() != null) {
            if (this.getProject().getStories() == null)
                this.getProject().setStories(new HashSet<>());
            this.getProject().getStories().add(this);
        }
        if (this.getEducation() != null) {
            if (this.getEducation().getStories() == null)
                this.getEducation().setStories(new HashSet<>());
            this.getEducation().getStories().add(this);
        }
        if (this.getExperience() != null) {
            if (this.getExperience().getStories() == null)
                this.getExperience().setStories(new HashSet<>());
            this.getExperience().getStories().add(this);
        }
    }

    @Override
    public void removeRelationships() {
        if (this.getDiary() != null) {
            this.getDiary().getStories().remove(this);
        }
        if (this.getProject() != null) {
            this.getProject().getStories().remove(this);
        }
        if (this.getEducation() != null) {
            this.getEducation().getStories().remove(this);
        }
        if (this.getEducation() != null) {
            this.getEducation().getStories().remove(this);
        }
    }

    @Override
    public void clearRelationships() {
        this.skills = null;
    }

    //////////////////////
    //  Custom methods  //
    //////////////////////

    public Integer getDiaryId() {
        if (this.getDiary() == null)
            return null;
        return this.getDiary().getId();
    }

    public Integer getProjectId() {
        if (this.getProject() == null)
            return -1;
        return this.getProject().getId();
    }

    public Integer getEducationId() {
        if (this.getEducation() == null)
            return -1;
        return this.getEducation().getId();
    }

    public Integer getExperienceId() {
        if (this.getExperience() == null)
            return -1;
        return this.getExperience().getId();
    }

    public Integer getEntityId(Class<?> clazz) {
        if (clazz.equals(Diary.class))
            return this.getDiaryId();
        if (clazz.equals(Project.class))
            return this.getProjectId();
        if (clazz.equals(Education.class))
            return this.getEducationId();
        if (clazz.equals(Experience.class))
            return this.getExperienceId();
        return null;
    }
}
