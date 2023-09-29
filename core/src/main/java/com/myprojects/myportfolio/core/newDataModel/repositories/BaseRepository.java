package com.myprojects.myportfolio.core.newDataModel.repositories;

import com.myprojects.myportfolio.core.newDataModel.dao.BaseDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BaseRepository<T extends BaseDao, Integer> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {

    default Optional<T> findBySlug(String slug) {
        // If an entity needs this method, it should override it
        return Optional.empty();
    }

    default Optional<T> findBySlugConstraint(Object entity, String slug) {
        // If an entity needs this method, it should override it
        return Optional.empty();
    }

}
