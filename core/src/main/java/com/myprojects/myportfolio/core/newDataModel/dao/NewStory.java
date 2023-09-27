package com.myprojects.myportfolio.core.newDataModel.dao;

import com.myprojects.myportfolio.core.newDataModel.utils.SlugSource;
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
@Table(name = "new_stories")
public class NewStory extends SlugDao {

    @Serial
    private static final long serialVersionUID = 3219080188292925051L;

    public enum FIELDS {
        ID("id"),
        CREATEDAT("createdAt"),
        UPDATEDAT("updatedAt"),
        DIARYID("diaryId"),
        SLUG("slug"),
        TITLE("title"),
        DESCRIPTION("description"),
        FROMDATE("fromDate"),
        TODATE("toDate"),
        ISPRIMARYSTORY("isPrimaryStory"),
        FIRSTRELEVANTSECTION("firstRelevantSection"),
        SECONDRELEVANTSECTION("secondRelevantSection");

        FIELDS(String name) {
        }
    }

    @SlugSource
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "DATE")
    private LocalDate fromDate;

    @Column(columnDefinition = "DATE")
    private LocalDate toDate;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPrimaryStory;

    @Column(columnDefinition = "TEXT")
    private String firstRelevantSection;

    @Column(columnDefinition = "TEXT")
    private String secondRelevantSection;

    // When creating a story, we have to specify an already existing diaryId
    // When updating a story, we can also update the diaryId
    // When deleting a story, the relation with the diary is deleted
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "diary_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "new_diary_story_fk"
            )
    )
    private NewDiary diary;

    // NewProject is the owner of the relationship.
    // When creating a story, we have to specify an already existing projectId
    // When updating a story, nothing happens to the relationship (no add, no delete)
    // When deleting a story, the relation with the project is deleted
    @ManyToMany(mappedBy = "stories", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<NewProject> projects = new HashSet<>();

    @Override
    public void completeRelationships() {
        this.getProjects().forEach(project ->
                project.getStories().add(this)
        );
        if (this.getDiary() != null) {
            if (this.getDiary().getStories() == null)
                this.getDiary().setStories(new HashSet<>());
            this.getDiary().getStories().add(this);
        }
    }

    @Override
    public void removeRelationships() {
        this.getProjects().forEach(project ->
                project.getStories().remove(this)
        );
        if (this.getDiary() != null) {
            this.getDiary().getStories().remove(this);
        }
    }
}
