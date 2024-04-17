package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.myprojects.myportfolio.core.aspects.interfaces.SlugSource;
import com.myprojects.myportfolio.core.configAndUtils.UserCustomSeqGen;
import com.myprojects.myportfolio.core.dao.enums.Sex;
import com.myprojects.myportfolio.core.dao.skills.UserSkill;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"slug"})})
@GenericGenerator(
        name = "default_gen",
        type = UserCustomSeqGen.class,
        parameters = {
                @org.hibernate.annotations.Parameter(name = "sequence_name", value = "user_seq"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
        }
)
@Cache(region = "users", usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends SlugDao {

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
        PROFESSION("profession"),
        PRESENTATION("presentation"),
        MAIN_STORY_ID("mainStoryId"),
        CUSTOMIZATIONS("customizations"),
        ;

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

    private String profession;

    private String presentation;

    private Integer mainStoryId;

    @Column(columnDefinition = "TEXT")
    private String customizations = "{}";

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
    @Cache(region = "diaries", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<Diary> diaries = new HashSet<>();

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
    @Cache(region = "projects", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<Project> projects = new HashSet<>();

    /**
     * @Owner: User is the owner of the relationship.
     * @Create&Update: When creating or updating a user, you can't create or update educations.
     *                 This is because it's not necessary. You can use the education controller to create or update educations.
     * @Delete: When deleting a user, the educations ARE DELETED because the user is the owner of the education itself.
     */
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    @Cache(region = "educations", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<Education> educations = new HashSet<>();

    /**
     * @Owner: User is the owner of the relationship.
     * @Create&Update: When creating or updating a user, you can't create or update experiences.
     *                 This is because it's not necessary. You can use the education controller to create or update experiences.
     * @Delete: When deleting a user, the experiences ARE DELETED because the user is the owner of the experience itself.
     */
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    @Cache(region = "experiences", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<Experience> experiences = new HashSet<>();

    /**
     * @Owner: User is the owner of the relationship.
     * @Create&Update: When creating or updating a user, you can create or update the relation UserSkill.
     *                 You have to specify the skill id and, in the update, also the user id.
     * @Delete: When deleting a user, the experiences ARE DELETED because the user is the owner of the experience itself.
     */
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    @JsonManagedReference
    @Builder.Default
    @Cache(region = "userSkills", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<UserSkill> skills = new HashSet<>();

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
            this.getSkills().forEach(skill ->
                    skill.setUser(this)
            );
        }
    }

    @Override
    public void clearRelationships() {
        this.diaries = null;
        this.projects = null;
        this.educations = null;
        this.experiences = null;
        this.skills = null;
    }

    public User(Integer id, String email) {
        this.id = id;
        this.email = email;
        this.setSlug("");
    }

    public User(Integer id, String email, String slug) {
        this.id = id;
        this.email = email;
        this.setSlug(slug);
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    //////////////////////////////////////////////////
    // CUSTOMIZATIONS UTILITY METHODS
    //////////////////////////////////////////////////

    @Getter
    public enum CustomizationsKeysEnum {
        PROFILE_IMAGE("profileImage"),
        CURRICULUM_VITAE("CV"),
        ;

        private final String key;

        CustomizationsKeysEnum(String key) {
            this.key = key;
        }
    }

    // add element to the json customizations
    public void addToCustomizations(String key, String value) {
        if (this.customizations == null) {
            this.customizations = "{}";
        }
        JsonObject json = new Gson().fromJson(customizations, JsonObject.class);
        json.addProperty(key, value);
        customizations = json.toString();
    }

    // remove element from the json customizations
    public void removeFromCustomizations(String key) {
        if (this.customizations == null) {
            this.customizations = "{}";
        }
        JsonObject json = new Gson().fromJson(customizations, JsonObject.class);
        if (json.has(key)) {
            json.remove(key);
        }
        customizations = json.toString();
    }

    // get element from the json customizations as string
    public String getStringFromCustomizations(String key) {
        if (this.customizations == null) {
            this.customizations = "{}";
        }
        JsonObject json = new Gson().fromJson(customizations, JsonObject.class);
        JsonElement jsonElement = json.has(key) ? json.get(key) : null;
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        return jsonElement.getAsString();
    }

    // get element from the json customizations as Boolean
    public Boolean getBooleanFromCustomizations(String key) {
        if (this.customizations == null) {
            this.customizations = "{}";
        }
        JsonObject json = new Gson().fromJson(customizations, JsonObject.class);
        return json.has(key) ? json.get(key).getAsBoolean() : null;
    }

}
