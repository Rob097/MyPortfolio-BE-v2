package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.dao.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

public interface BaseServiceI<T extends BaseDao> {

    Slice<T> findAll(Specification<T> specification, Pageable pageable);

    T findBy(Specification<T> specification);

    T findById(Integer id);

    T save(T t);

    T update(T t);

    void delete(T t);

}
