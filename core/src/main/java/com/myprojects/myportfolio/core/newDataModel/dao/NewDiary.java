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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "new_user_diary_fk"
            )
    )
    private NewUser user;

    @OneToMany(
            mappedBy = "diary",
            orphanRemoval = true,
            cascade = {CascadeType.ALL}, // Diary can create, update, delete stories
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
}
