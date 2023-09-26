package com.myprojects.myportfolio.core.newDataModel.dao;

import com.myprojects.myportfolio.core.newDataModel.utils.SlugSource;
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
@Table(name = "new_projects")
public class NewProject extends SlugDao {

    @Serial
    private static final long serialVersionUID = -9073812554333350801L;

    @SlugSource
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "new_user_project_fk"
            )
    )
    private NewUser user;

    // This way NewProject is the owner of the relationship.
    // This means that when we create a new project, the stories will be created automatically.
    // But, if we create a new story, the project will not be created automatically.
    // And this is the right way to do it.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "new_project_stories",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"))
    @Builder.Default
    private Set<NewStory> stories = new HashSet<>();

    @Override
    public void completeRelationships() {
        this.getStories().forEach(story -> story.getProjects().add(this));
        if (this.getUser() != null) {
            if (this.getUser().getProjects() == null)
                this.getUser().setProjects(new HashSet<>());
            this.getUser().getProjects().add(this);
        }
    }

}
