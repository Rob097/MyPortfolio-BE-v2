package com.myprojects.myportfolio.core.newDataModel.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "new_users")
public class NewUser extends AuditableDao {

    @Serial
    private static final long serialVersionUID = -7807186737167318556L;

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

        FIELDS(String name) {
        }
    }

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
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<NewDiary> diaries = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<NewProject> projects = new HashSet<>();

    @Override
    public void completeRelationships() {
        if (this.getDiaries() != null) {
            this.getDiaries().forEach(diary -> diary.setUser(this));
        }
        if (this.getProjects() != null) {
            this.getProjects().forEach(project -> project.setUser(this));
        }
    }

    public enum Sex {
        MALE,
        FEMALE;
    }

    public NewUser(Integer id, String email) {
        this.id = id;
        this.email = email;
        this.slug = "";
    }

    public NewUser(Integer id, String email, String slug) {
        this.id = id;
        this.email = email;
        this.slug = slug;
    }

}
