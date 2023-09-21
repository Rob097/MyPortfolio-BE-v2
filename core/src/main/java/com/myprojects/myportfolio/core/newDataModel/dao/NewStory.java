package com.myprojects.myportfolio.core.newDataModel.dao;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    @Expose
    @Column(unique = true, nullable = false)
    private String slug;

    @Expose
    @Column(nullable = false)
    private String title;

    @Expose
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Expose
    @Column(columnDefinition = "DATE")
    private LocalDate fromDate;

    @Expose
    @Column(columnDefinition = "DATE")
    private LocalDate toDate;

    @Expose
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPrimaryStory;

    @Expose
    @Column(columnDefinition = "TEXT")
    private String firstRelevantSection;

    @Expose
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

}
