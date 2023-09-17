package com.myprojects.myportfolio.core.diary;

import com.myprojects.myportfolio.core.story.Story;
import com.myprojects.myportfolio.core.user.User;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diaries")
@org.hibernate.annotations.Cache(region = "diaries", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Diary implements Serializable {

    @Serial
    private static final long serialVersionUID = 4435134181331891457L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime entryDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_diary_fk"
            )
    )
    @org.hibernate.annotations.Cache(region = "users", usage=CacheConcurrencyStrategy.READ_ONLY)
    private User user;

    @OneToMany(
            mappedBy = "diary",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    @org.hibernate.annotations.Cache(region = "stories", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<Story> stories;

    public void addStory(Story story) {
        stories.add(story);
        story.setDiary(this);
    }

    public void removeStory(Story story) {
        stories.remove(story);
        story.setDiary(null);
    }

}
