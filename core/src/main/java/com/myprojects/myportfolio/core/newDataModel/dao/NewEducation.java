package com.myprojects.myportfolio.core.newDataModel.dao;

import com.myprojects.myportfolio.core.newDataModel.aspects.interfaces.SlugSource;
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
@Table(name = "new_educations")
public class NewEducation extends SlugDao {

    @Serial
    private static final long serialVersionUID = 1569828648829514030L;

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

    // When Creating an Education, we need to pass an existing userId.
    // When Updating an education, the already connected user is left untouched.
    // When Deleting an education, the user is not deleted but the relationship is deleted.
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
    private NewUser user;

    // NewEducation is the owner of the relationship.
    // When Creating a new Education or Updating an existing education is possible to create a new story or connect an existing story.
    // When Updating an education, the already connected stories are left untouched.
    // When Deleting an education, the stories are not deleted but the relationship is deleted.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "new_education_stories",
            joinColumns = @JoinColumn(name = "education_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"))
    @Builder.Default
    private Set<NewStory> stories = new HashSet<>();

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

}
