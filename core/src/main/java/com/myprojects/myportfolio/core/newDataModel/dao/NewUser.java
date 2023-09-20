package com.myprojects.myportfolio.core.newDataModel.dao;

import com.google.gson.annotations.Expose;
import com.myprojects.myportfolio.core.diary.Diary;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "new_users")
public class NewUser extends AuditableDao {

    @Serial
    private static final long serialVersionUID = -7807186737167318556L;

    /*@Id
    @Expose
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "com.myprojects.myportfolio.clients.utils.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    protected Integer id;*/

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

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Diary> diaries;

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
