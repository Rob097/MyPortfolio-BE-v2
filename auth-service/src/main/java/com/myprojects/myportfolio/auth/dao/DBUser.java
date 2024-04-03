package com.myprojects.myportfolio.auth.dao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Cache(region = "users", usage = CacheConcurrencyStrategy.READ_WRITE)
public class DBUser {

    @Getter
    public enum CustomizationKeysEnum {
        IS_SET("isSet");

        private final String key;

        CustomizationKeysEnum(String key) {
            this.key = key;
        }
    }

    @Id
    @Column(
            name = "id"
    )
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;

    private String customizations = "{}";

    @Transient
    private String token;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @ToString.Exclude
    @Cache(region = "roles", usage = CacheConcurrencyStrategy.READ_ONLY)
    private Set<DBRole> roles;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DBUser dbUser = (DBUser) o;
        return getId() != null && Objects.equals(getId(), dbUser.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public void addToCustomizations(String key, boolean value) {
        if (customizations == null) {
            customizations = "{}";
        }
        JsonObject json = new Gson().fromJson(customizations, JsonObject.class);
        json.addProperty(key, value);
        customizations = json.toString();
    }

}
