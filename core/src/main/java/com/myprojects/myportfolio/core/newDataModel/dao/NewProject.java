package com.myprojects.myportfolio.core.newDataModel.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.newDataModel.aspects.interfaces.SlugSource;
import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewSkill;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "new_projects", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "slug" }) })
public class NewProject extends SlugDao {

    @Serial
    private static final long serialVersionUID = -9073812554333350801L;

    @Getter
    @AllArgsConstructor
    public enum FIELDS {
        ID("id"),
        CREATEDAT("createdAt"),
        UPDATEDAT("updatedAt"),
        SLUG("slug"),
        TITLE("title"),
        DESCRIPTION("description"),
        ;

        private final String name;
    }

    @SlugSource
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // When Creating a Project, we need to pass an existing userId.
    // When Updating a project, the already connected user is left untouched.
    // When Deleting a project, the user is not deleted but the relationship is deleted.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "new_user_project_fk"
            )
    )
    @JsonBackReference
    @Builder.Default
    private NewUser user = new NewUser();

    // NewProject is the owner of the relationship.
    // When Creating a new Project or Updating an existing project is possible to create a new story or connect an existing story.
    // When Updating a project, the already connected stories are left untouched.
    // When Deleting a project, the stories are not deleted but the relationship is deleted.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "new_project_stories",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"))
    @JsonManagedReference
    @Builder.Default
    private Set<NewStory> stories = new HashSet<>();

    // When creating a project, we can specify a list of ALREADY EXISTING skills (the ID must be present)
    // When updating a project, we can also update the skills.
    //    - If a skill is already in the list, nothing happens
    //    - If a skill is not in the list, it is added
    //    - If a skill is in the list but not in the new list, it is removed (ATTENTION)
    // When deleting a project, the relationship is removed
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "new_project_skills",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    @Builder.Default
    private Set<NewSkill> skills = new HashSet<>();

    @Override
    public void completeRelationships() {
        this.getStories().forEach(story -> story.getProjects().add(this));
        if (this.getUser() != null) {
            if (this.getUser().getProjects() == null)
                this.getUser().setProjects(new HashSet<>());
            this.getUser().getProjects().add(this);
        }
    }

    @Override
    public void removeRelationships() {
        this.getStories().forEach(story -> story.getProjects().remove(this));
        if (this.getUser() != null) {
            this.getUser().getProjects().remove(this);
        }
    }

    //////////////////////
    //  Custom methods  //
    //////////////////////

    public Integer getUserId() {
        if(this.getUser()==null)
            return null;
        return this.getUser().getId();
    }
}
