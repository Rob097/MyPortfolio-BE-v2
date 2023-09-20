package com.myprojects.myportfolio.core.newDataModel.dao;

import com.google.gson.annotations.Expose;
import com.myprojects.myportfolio.core.story.Story;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "new_diaries")
public class NewDiary extends AuditableDao {

    @Serial
    private static final long serialVersionUID = -9073812554333350801L;

    @Expose
    private String title;

    @Expose
    private String description;

    @Expose
    private Boolean isMain;

    @Expose
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

    @Expose
    @OneToMany(
            mappedBy = "diary",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private Set<NewStory> stories;

}
