package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.aspects.interfaces.SlugSource;
import com.myprojects.myportfolio.core.dao.skills.Skill;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "projects", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "slug" }) })
@SequenceGenerator(name = "default_gen", sequenceName = "project_seq", allocationSize = 1)
@Cache(region = "projects", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project extends SlugDao implements WithStoriesDao {

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
        MAIN_STORY_ID("mainStoryId"),
        ;

        private final String name;
    }

    @SlugSource
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private Integer mainStoryId;

    /**
     * @Create: When Creating a Project, we need to pass an existing userId.
     * @Update: When Updating a project, the already connected user is left untouched.
     * @Delete: When Deleting a project, the user is not deleted but the relationship is deleted.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_project_fk"
            )
    )
    @JsonBackReference
    @Builder.Default
    @Cache(region = "users", usage=CacheConcurrencyStrategy.READ_ONLY)
    private User user = new User();

    /**
     * @Owner: Project is the owner of the relationship.
     * @Create: When Creating a new Project or Updating an existing project you can only create new stories.
     *          This is because is a responsibility of the story itself to decide which project to associate to.
     *          However, you can update existing stories that are already connected to the entity.
     * @Update: When Updating a project, the already connected stories are updated but not removed if not passed.
     * @Delete: When Deleting a project, the stories are not deleted but the relationship is deleted.
     */
    @OneToMany(
            mappedBy = "project",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    )
    @JsonManagedReference
    @Builder.Default
    @Cache(region = "stories", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Story> stories = new HashSet<>();

    /**
     * @Create: When creating a project, we can specify a list of ALREADY EXISTING skills (the ID must be present)
     * @Update: When updating a project, we can also update the skills.
     *          - If a skill is already in the list, nothing happens
     *          - If a skill is not in the list, it is added
     *          - If a skill is in the list but not in the new list, it is removed (ATTENTION)
     * @Delete: When deleting a project, the relationship is removed
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_skills",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    @Builder.Default
    @Cache(region = "skills", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Skill> skills = new HashSet<>();

    @Override
    public void completeRelationships() {
        if(this.getStories()!=null) {
            this.getStories().forEach(story -> story.setProject(this));
        }
        if (this.getUser() != null) {
            if (this.getUser().getProjects() == null)
                this.getUser().setProjects(new HashSet<>());
            this.getUser().getProjects().add(this);
        }
    }

    @Override
    public void removeRelationships() {
        if(this.getStories()!=null) {
            this.getStories().forEach(story -> story.setProject(null));
        }
        if (this.getUser() != null) {
            this.getUser().getProjects().remove(this);
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
        if(this.getUser()==null)
            return null;
        return this.getUser().getId();
    }
}
