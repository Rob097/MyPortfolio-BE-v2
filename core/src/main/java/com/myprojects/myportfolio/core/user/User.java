package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.education.Education;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.project.Project;
import com.myprojects.myportfolio.core.skill.UserSkill;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "com.myprojects.myportfolio.clients.utils.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @Column(unique = true, nullable = false)
    protected Integer id;

    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private Integer age;

    private String nationality;

    private String nation;

    private String province;

    private String city;

    private String cap;

    private String address;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToMany(
            mappedBy = "owner",
            orphanRemoval = true,
            // PERSIST means that if I create a User entity with some Projects, it also creates the projects
            // REMOVE means that if I delete a User entity with some Projects, it also deletes the projects
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Project> projects;

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Education> educationList;

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Experience> experienceList;

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<UserSkill> skills;

    @OneToOne(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private Diary diary;

    public enum Sex{
        MALE,
        FEMALE;
    }

}
