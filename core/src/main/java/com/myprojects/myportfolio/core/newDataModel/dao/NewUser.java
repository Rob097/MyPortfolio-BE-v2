package com.myprojects.myportfolio.core.newDataModel.dao;

import com.myprojects.myportfolio.core.newDataModel.aspects.interfaces.SlugSource;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "new_users", uniqueConstraints = { @UniqueConstraint(columnNames = { "slug" }) })
public class NewUser extends SlugDao {

    @Serial
    private static final long serialVersionUID = -7807186737167318556L;

    @Getter
    @AllArgsConstructor
    public enum FIELDS {
        ID("id"),
        CREATEDAT("createdAt"),
        UPDATEDAT("updatedAt"),
        SLUG("slug"),
        FIRSTNAME("firstName"),
        LASTNAME("lastName"),
        EMAIL("email"),
        PHONE("phone"),
        AGE("age"),
        NATIONALITY("nationality"),
        NATION("nation"),
        PROVINCE("province"),
        CITY("city"),
        CAP("cap"),
        ADDRESS("address"),
        SEX("sex"),
        TITLE("title"),
        DESCRIPTION("description");

        private final String name;
    }

    @SlugSource
    private String firstName;

    @SlugSource
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
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<NewDiary> diaries = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<NewProject> projects = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<NewEducation> educations = new HashSet<>();

    @Override
    public void completeRelationships() {
        if (this.getDiaries() != null) {
            this.getDiaries().forEach(diary -> {
                diary.setUser(this);
                diary.completeRelationships();
            });
        }
        if (this.getProjects() != null) {
            this.getProjects().forEach(project -> {
                project.setUser(this);
                project.completeRelationships();
            });
        }
        if (this.getEducations() != null) {
            this.getEducations().forEach(education -> {
                education.setUser(this);
                education.completeRelationships();
            });
        }
    }

    public enum Sex {
        MALE,
        FEMALE
    }

    public NewUser(Integer id, String email) {
        this.id = id;
        this.email = email;
        this.setSlug("");
    }

    public NewUser(Integer id, String email, String slug) {
        this.id = id;
        this.email = email;
        this.setSlug(slug);
    }

}
