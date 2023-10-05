package com.myprojects.myportfolio.core.newDataModel.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myprojects.myportfolio.core.newDataModel.aspects.interfaces.SlugSource;
import com.myprojects.myportfolio.core.newDataModel.dao.enums.Sex;
import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewUserSkill;
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
@Table(name = "new_users", uniqueConstraints = {@UniqueConstraint(columnNames = {"slug"})})
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

    /**
     * @Owner: User is the owner of the relationship.
     * @Create: When creating a new user you can only create new diaries. You can't connect existing diaries.
     * @Update: When updating a user, you can't create or update diaries.
     *          This is because it's not necessary. You can use the diary controller to create or update diaries.
     * @Delete: When deleting a user, the diaries ARE DELETED because the user is the owner of the diary itself.
     */
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    private Set<NewDiary> diaries = new HashSet<>();

    /**
     * @Owner: User is the owner of the relationship.
     * @Create&Update: When creating or updating a user, you can't create or update projects.
     *                 This is because it's not necessary. You can use the project controller to create or update projects.
     * @Delete: When deleting a user, the projects ARE DELETED because the user is the owner of the project itself.
     */
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    private Set<NewProject> projects = new HashSet<>();

    // User is the owner of the relationship.
    // When creating or updating a user, you can't create or update educations.
    // This is because it's not necessary. You can use the education controller to create or update educations.
    // When deleting a user, the educations ARE DELETED because the user is the owner of the education itself.
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    private Set<NewEducation> educations = new HashSet<>();

    // User is the owner of the relationship.
    // When creating or updating a user, you can't create or update experiences.
    // This is because it's not necessary. You can use the education controller to create or update experiences.
    // When deleting a user, the experiences ARE DELETED because the user is the owner of the experience itself.
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    private Set<NewExperience> experiences = new HashSet<>();

    // User is the owner of the relationship.
    // When creating or updating a user, you can't create or update skills but, you can create or update the relation NewUserSkill.
    // When deleting a user, the experiences ARE DELETED because the user is the owner of the experience itself.
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = { CascadeType.MERGE },
            fetch = FetchType.LAZY)
    @JsonManagedReference
    @Builder.Default
    private Set<NewUserSkill> skills = new HashSet<>();

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
        if (this.getExperiences() != null) {
            this.getExperiences().forEach(experience -> {
                experience.setUser(this);
                experience.completeRelationships();
            });
        }
        if (this.getSkills() != null) {
            this.getSkills().forEach(skill -> {
                skill.setUser(this);
            });
        }
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
