package com.myprojects.myportfolio.core.newDataModel.dao;

import com.google.gson.annotations.Expose;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "new_projects")
public class NewProject extends AuditableDao {

    @Serial
    private static final long serialVersionUID = -9073812554333350801L;

    @Expose
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            insertable = false,
            updatable = false,
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
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "new_project_stories",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "story_id", referencedColumnName = "id"))
    private Set<NewStory> stories;

}