package com.myprojects.myportfolio.auth.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
public class DBPermission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name="id"
    )
    private Long id;
    private String name;
    private String description;

    @Override
    public String getAuthority() {
        return name;
    }
}
