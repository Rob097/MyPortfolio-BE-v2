package com.myprojects.myportfolio.core.newDataModel.dao;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    @Expose
    @Column(unique = true, nullable = false)
    private String slug;

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    @Column(nullable = false, unique = true)
    private String email;

    @Expose
    private String phone;

    @Expose
    private Integer age;

    @Expose
    private String nationality;

    @Expose
    private String nation;

    @Expose
    private String province;

    @Expose
    private String city;

    @Expose
    private String cap;

    @Expose
    private String address;

    @Expose
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Expose
    private String title;

    @Expose
    private String description;

    @Expose
    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<NewDiary> diaries;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewUser user = (NewUser) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
