package com.myprojects.myportfolio.core.newDataModel.dao;

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
@Table(name = "new_diaries")
public class NewDiary extends AuditableDao {

    @Serial
    private static final long serialVersionUID = -9073812554333350801L;

    private String title;

    private String description;

    private Boolean isMain;

    // When Creating a Diary, we need to pass an existing userId.
    // When Updating a Diary, the already connected user is left untouched.
    // When Deleting a Diary, the user is not deleted but the relationship is deleted.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "new_user_diary_fk"
            )
    )
    private NewUser user;

    // Diary is the owner of the relationship.
    // When creating or updating a diary you can only create new stories. You can't connect existing stories or remove existing stories that are already connected.
    // This is because is a responsibility of the story itself or of the current diary of the story to decide which diary is the owner.
    // When deleting a diary, the stories ARE DELETED because the diary is the owner of the story itself.
    @OneToMany(
            mappedBy = "diary",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @Builder.Default
    private Set<NewStory> stories = new HashSet<>();

    @Override
    public void completeRelationships() {
        if (this.getStories() != null) {
            this.getStories().forEach(story -> story.setDiary(this));
        }
        if (this.getUser() != null) {
            if (this.getUser().getDiaries() == null)
                this.getUser().setDiaries(new HashSet<>());
            this.getUser().getDiaries().add(this);
        }
    }

    @Override
    public void removeRelationships() {
        if (this.getUser() != null) {
            this.getUser().getDiaries().remove(this);
        }
    }
}
