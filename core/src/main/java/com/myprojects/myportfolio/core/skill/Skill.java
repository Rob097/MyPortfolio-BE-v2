package com.myprojects.myportfolio.core.skill;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.myprojects.myportfolio.core.story.Story;
import com.myprojects.myportfolio.core.user.User;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skills")
@org.hibernate.annotations.Cache(region = "skills", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Skill implements Serializable {

    private static final long serialVersionUID = 4898256361830275290L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "skills")
    @org.hibernate.annotations.Cache(region = "users", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<User> users;

}
