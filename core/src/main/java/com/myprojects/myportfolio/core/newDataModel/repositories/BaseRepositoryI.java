package com.myprojects.myportfolio.core.newDataModel.repositories;

import com.myprojects.myportfolio.core.newDataModel.dao.BaseDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseRepositoryI<T extends BaseDao, Integer> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {
}
