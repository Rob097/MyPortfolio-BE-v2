package com.myprojects.myportfolio.core.newDataModel.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class NewStory extends AuditableDao {

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

    @Column(unique = true, nullable = false)
    private String slug;

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

    @ManyToMany(mappedBy = "stories", fetch = FetchType.LAZY)
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
}
