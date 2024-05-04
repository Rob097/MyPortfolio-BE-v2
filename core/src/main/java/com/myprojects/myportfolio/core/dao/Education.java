package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "educations", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "slug"})})
@SequenceGenerator(name = "default_gen", sequenceName = "education_seq", allocationSize = 1)
@Cache(region = "educations", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Education extends SlugDao implements WithStoriesDao {

    @Serial
    private static final long serialVersionUID = 1569828648829514030L;

    @Getter
    @AllArgsConstructor
    public enum FIELDS {
        ID("id"),
        CREATEDAT("createdAt"),
        UPDATEDAT("updatedAt"),
        SLUG("slug"),
        FROMDATE("fromDate"),
        TODATE("toDate"),
        FIELD("field"),
        SCHOOL("school"),
        DEGREE("degree"),
        GRADE("grade"),
        DESCRIPTION("description"),
        MAIN_STORY_ID("mainStoryId"),
        COVER_IMAGE("coverImage"),
        ;

        private final String name;
    }

    // Field of Study
    @SlugSource
    @Column(nullable = false)
    private String field;

    // School or University
    @SlugSource
    @Column(nullable = false)
    private String school;

    // Title of study
    @Column(nullable = false)
    private String degree;

    // Grade
    private Double grade;

    // Description
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "DATE DEFAULT (CURRENT_DATE)")
    private LocalDate fromDate;

    @Column(columnDefinition = "DATE")
    private LocalDate toDate;

    private Integer mainStoryId;

    private String coverImage;

    /**
     * @Create: When Creating an Education, we need to pass an existing userId.
     * @Update: When Updating an education, the already connected user is left untouched.
     * @Delete: When Deleting an education, the user is not deleted but the relationship is deleted.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_education_fk"
            )
    )
    @JsonBackReference
    @Builder.Default
    @Cache(region = "users", usage = CacheConcurrencyStrategy.READ_ONLY)
    private User user = new User();

    /**
     * @Owner: Education is the owner of the relationship.
     * @Create: When Creating a new Education or Updating an existing education you can only create new stories.
     *          This is because is a responsibility of the story itself to decide which education to associate to.
     *          However, you can update existing stories that are already connected to the entity.
     * @Update: When Updating an education, the already connected stories are updated but not removed if not passed.
     * @Delete: When Deleting an education, the stories are not deleted but the relationship is deleted.
     */
    @OneToMany(
            mappedBy = "education",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    )
    @JsonManagedReference
    @Builder.Default
    @OrderBy("orderInProject ASC, updatedAt DESC")
    @Cache(region = "stories", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<Story> stories = new HashSet<>();

    /**
     * @Create: When creating an education, we can specify a list of ALREADY EXISTING skills
     * @Update: When updating an education, we can also update the skills.
     *          - If a skill is already in the list, nothing happens
     *          - If a skill is not in the list, it is added
     *          - If a skill is in the list but not in the new list, it is removed (ATTENTION)
     * @Delete: When deleting an education, the relationship is removed
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "education_skills",
            joinColumns = @JoinColumn(name = "education_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    @Builder.Default
    @Cache(region = "skills", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<Skill> skills = new HashSet<>();

    @Override
    public void completeRelationships() {
        if (this.getStories() != null) {
            this.getStories().forEach(story -> {
                story.setEducation(this);
                story.completeRelationships();
            });
        }
        if (this.getUser() != null) {
            if (this.getUser().getEducations() == null)
                this.getUser().setEducations(new HashSet<>());
            this.getUser().getEducations().add(this);
        }
    }

    @Override
    public void removeRelationships() {
        if (this.getStories() != null) {
            this.getStories().forEach(story -> story.setEducation(null));
        }
        if (this.getUser() != null) {
            this.getUser().getEducations().remove(this);
        }
    }

    @Override
    public void clearRelationships() {
        this.stories = null;
        this.skills = null;
    }

    //////////////////////
    //  Custom methods  //
    //////////////////////

    public Integer getUserId() {
        if (this.getUser() == null)
            return null;
        return this.getUser().getId();
    }

}
