package com.myprojects.myportfolio.core.newDataModel.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;

@SuppressWarnings("JpaEntityListenerInspection")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class AuditableDao extends BaseDao {

    @Column(name = "CreatedAt", nullable = false)
    private Timestamp createdAt;

    @Column(name = "UpdatedAt", nullable = false)
    private Timestamp updatedAt;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Timestamp(System.currentTimeMillis());
        if (this.updatedAt == null) updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

}
