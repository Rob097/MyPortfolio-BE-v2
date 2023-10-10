package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.aspects.interfaces.SlugSource;
import com.myprojects.myportfolio.core.dao.skills.NewSkill;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
@Table(name = "new_educations", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "slug"})})
public class NewEducation extends SlugDao {

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

    @Column(columnDefinition = "DATE")
    private LocalDate fromDate;

    @Column(columnDefinition = "DATE")
    private LocalDate toDate;

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
                    name = "new_user_education_fk"
            )
    )
    @JsonBackReference
    @Builder.Default
    private NewUser user = new NewUser();

    /**
     * @Owner: NewEducation is the owner of the relationship.
     * @Create: When Creating a new Education or Updating an existing education is possible to create a new story or connect an existing story.
     * @Update: When Updating an education, the already connected stories are left untouched.
     * @Delete: When Deleting an education, the stories are not deleted but the relationship is deleted.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "new_education_stories",
            joinColumns = @JoinColumn(name = "education_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"))
    @JsonManagedReference
    @Builder.Default
    private Set<NewStory> stories = new HashSet<>();

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
            name = "new_education_skills",
            joinColumns = @JoinColumn(name = "education_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    @Builder.Default
    private Set<NewSkill> skills = new HashSet<>();

    @Override
    public void completeRelationships() {
        this.getStories().forEach(story -> story.getEducations().add(this));
        if (this.getUser() != null) {
            if (this.getUser().getEducations() == null)
                this.getUser().setEducations(new HashSet<>());
            this.getUser().getEducations().add(this);
        }
    }

    @Override
    public void removeRelationships() {
        this.getStories().forEach(story -> story.getEducations().remove(this));
        if (this.getUser() != null) {
            this.getUser().getEducations().remove(this);
        }
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
