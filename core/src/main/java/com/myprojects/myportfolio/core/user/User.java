package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.core.project.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name="id"
    )
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(
            mappedBy = "owner",
            orphanRemoval = true,
            // In this case I don't want any cascade
            // cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    private List<Project> projects;


}
