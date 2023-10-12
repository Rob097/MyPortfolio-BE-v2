package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "diaries")
public class Diary extends AuditableDao {

    @Serial
    private static final long serialVersionUID = -9073812554333350801L;

    private String title;

    private String description;

    private Boolean isMain;

    /**
     * @Create: When Creating a Diary, we need to pass an existing userId.
     * @Update: When Updating a Diary, the already connected user is left untouched.
     * @Delete: When Deleting a Diary, the user is not deleted but the relationship is deleted.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            updatable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_diary_fk"
            )
    )
    @JsonBackReference
    @Builder.Default
    private User user = new User();

    /**
     * @Owner: Diary is the owner of the relationship.
     * @Create: When creating or updating a diary you can only create new stories. You can't connect existing stories or remove existing stories that are already connected.
     * @Update: This is because is a responsibility of the story itself or of the current diary of the story to decide which diary is the owner.
     * @Delete: When deleting a diary, the stories ARE DELETED because the diary is the owner of the story itself.
     */
    @OneToMany(
            mappedBy = "diary",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    @Builder.Default
    private Set<Story> stories = new HashSet<>();

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

    //////////////////////
    //  Custom methods  //
    //////////////////////

    public Integer getUserId() {
        if(this.getUser()==null)
            return null;
        return this.getUser().getId();
    }
}
