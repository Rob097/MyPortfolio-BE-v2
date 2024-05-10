package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.BaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BaseServiceI<T extends BaseDao> {

    Page<T> findAll(Specification<T> specification, Pageable pageable);

    T findBy(Specification<T> specification);

    T findById(Integer id);

    T save(T t);

    T update(T t);

    void delete(T t);

}
