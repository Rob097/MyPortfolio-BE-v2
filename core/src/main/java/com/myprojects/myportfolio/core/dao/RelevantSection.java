package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "relevant_sections")
@SequenceGenerator(name = "default_gen", sequenceName = "relevant_sections_seq", allocationSize = 1)
@Cacheable
@Cache(region = "relevantSections", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RelevantSection extends BaseDao {

    @Serial
    private static final long serialVersionUID = -1722628783573444409L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "story_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "relevant_section_story_fk"
            )
    )
    @JsonBackReference
    @Builder.Default
    @Cache(region = "stories", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Story story = new Story();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private Integer orderInStory;

}
