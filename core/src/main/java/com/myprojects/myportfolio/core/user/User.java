package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.education.Education;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.project.Project;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@org.hibernate.annotations.Cache(region = "users", usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "com.myprojects.myportfolio.clients.utils.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @Column(unique = true, nullable = false)
    protected Integer id;

    @Column(unique = true, nullable = false)
    private String slug;

    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private Integer age;

    private String nationality;

    private String nation;

    private String province;

    private String city;

    private String cap;

    private String address;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String title;

    private String description;

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            // PERSIST means that if I create a User entity with some Projects, it also creates the projects
            // REMOVE means that if I delete a User entity with some Projects, it also deletes the projects
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    @org.hibernate.annotations.Cache(region = "projects", usage=CacheConcurrencyStrategy.READ_ONLY)
    private List<Project> projects;

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    @org.hibernate.annotations.Cache(region = "educations", usage=CacheConcurrencyStrategy.READ_ONLY)
    private List<Education> educations;

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    @org.hibernate.annotations.Cache(region = "experiences", usage=CacheConcurrencyStrategy.READ_ONLY)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cache(region = "userSkills", usage=CacheConcurrencyStrategy.READ_ONLY)
    private Set<UserSkill> skills;

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    @org.hibernate.annotations.Cache(region = "diaries", usage=CacheConcurrencyStrategy.READ_ONLY)
    private List<Diary> diaries;

    public enum Sex{
        MALE,
        FEMALE;
    }

    public User(Integer id, String email) {
        this.id = id;
        this.email = email;
        this.slug = "";
    }

    public User(Integer id, String email, String slug) {
        this.id = id;
        this.email = email;
        this.slug = slug;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}