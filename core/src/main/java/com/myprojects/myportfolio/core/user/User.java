package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.core.project.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
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
            // In this case I don't want any cascade
            // cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Project> projects;

    public enum Sex{
        MALE,
        FEMALE;
    }

}
