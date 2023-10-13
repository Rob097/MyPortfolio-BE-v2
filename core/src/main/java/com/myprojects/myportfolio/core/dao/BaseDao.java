package com.myprojects.myportfolio.core.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseDao implements Serializable {

    // @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "com.myprojects.myportfolio.core.configAndUtils.UseExistingIdOtherwiseGenerateUsingIdentity")
    // @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    protected Integer id;

    // TODO: Controllare perché non funziona nel caso l'id sia già valorizzato
    // Use the auto-generated id if it is not null, otherwise use the id passed in.
    public void setId(Integer id) {
        if (id != null && this.id == null) {
            this.id = id;
        }
    }

    public String toString() {
        return toJson();
    }

    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Error converting object to JSON: " + e.getMessage();
        }
    }

    /** Method overridden by all subClasses to populate its relations before persisting them into DB */
    @PrePersist
    public void completeRelationships() {/*Method gets overridden.*/ }

    /** Method overridden by all subClasses to remove its relations before deleting them from DB */
    @PreRemove
    public void removeRelationships() {/*Method gets overridden.*/ }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BaseDao baseDao = (BaseDao) o;
        return getId() != null && Objects.equals(getId(), baseDao.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}