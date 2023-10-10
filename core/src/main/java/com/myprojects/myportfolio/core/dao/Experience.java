package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.aspects.interfaces.SlugSource;
import com.myprojects.myportfolio.core.dao.enums.EmploymentTypeEnum;
import com.myprojects.myportfolio.core.dao.skills.Skill;
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
@Table(name = "experiences", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "slug"})})
public class Experience extends SlugDao {

    @Serial
    private static final long serialVersionUID = 6813981328660668957L;

    @Getter
    @AllArgsConstructor
    public enum FIELDS {
        ID("id"),
        CREATEDAT("createdAt"),
        UPDATEDAT("updatedAt"),
        SLUG("slug"),
        FROMDATE("fromDate"),
        TODATE("toDate"),
        TITLE("title"),
        DESCRIPTION("description"),
        EMPLOYMENT_TYPE("employmentType"),
        COMPANY_NAME("companyName"),
        LOCATION("location"),
        ;

        private final String name;
    }

    // Title (Ex: Full Stack Software Engineer)
    @SlugSource
    @Column(nullable = false)
    private String title;

    // Description
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private EmploymentTypeEnum employmentType;

    // Company Name (Ex: Microsoft)
    private String companyName;

    // Location (Ex: London, United Kingdom)
    private String location;

    // Start Date
    @Column(columnDefinition = "DATE")
    private LocalDate fromDate;

    // End Date (If null, still on course)
    @Column(columnDefinition = "DATE")
    private LocalDate toDate;

    /**
     * @Create: When Creating an Experience, we need to pass an existing userId.
     * @Update: When Updating an experience, the already connected user is left untouched.
     * @Delete: When Deleting an experience, the user is not deleted but the relationship is deleted.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_experience_fk"
            )
    )
    @JsonBackReference
    @Builder.Default
    private User user = new User();

    /**
     * @Owner: Experience is the owner of the relationship.
     * @Create: When Creating a new Experience or Updating an existing experience is possible to create a new story or connect an existing story.
     * @Update: When Updating an experience, the already connected stories are left untouched.
     * @Delete: When Deleting an experience, the stories are not deleted but the relationship is deleted.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "experience_stories",
            joinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"))
    @JsonManagedReference
    @Builder.Default
    private Set<Story> stories = new HashSet<>();

    /**
     * @Create: When creating an experience, we can specify a list of ALREADY EXISTING skills
     * @Update: When updating an experience, we can also update the skills.
     *          - If a skill is already in the list, nothing happens
     *          - If a skill is not in the list, it is added
     *          - If a skill is in the list but not in the new list, it is removed (ATTENTION)
     * @Delete: When deleting an experience, the relationship is removed
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "experience_skills",
            joinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();

    @Override
    public void completeRelationships() {
        this.getStories().forEach(story -> story.getExperiences().add(this));
        if (this.getUser() != null) {
            if (this.getUser().getExperiences() == null)
                this.getUser().setExperiences(new HashSet<>());
            this.getUser().getExperiences().add(this);
        }
    }

    @Override
    public void removeRelationships() {
        this.getStories().forEach(story -> story.getExperiences().remove(this));
        if (this.getUser() != null) {
            this.getUser().getExperiences().remove(this);
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
