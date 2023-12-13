package com.myprojects.myportfolio.core.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "relevantSections")
@SequenceGenerator(name = "default_gen", sequenceName = "relevant_sections_seq", allocationSize = 1)
@Cache(region = "relevantSections", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RelevantSection extends BaseDao {

    @Serial
    private static final long serialVersionUID = -1722628783573444409L;

    @ManyToOne(fetch = FetchType.LAZY)
    private Story story;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

}
