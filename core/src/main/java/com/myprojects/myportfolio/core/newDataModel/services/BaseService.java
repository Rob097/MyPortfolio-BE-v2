package com.myprojects.myportfolio.core.newDataModel.services;

import com.myprojects.myportfolio.clients.general.specifications.CoreSpecification;
import com.myprojects.myportfolio.core.newDataModel.dao.BaseDao;
import com.myprojects.myportfolio.core.newDataModel.repositories.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityNotFoundException;

@Slf4j
public abstract class BaseService<T extends BaseDao> implements BaseServiceI<T> {

    protected BaseRepository<T, Integer> repository;

    protected CoreSpecification<T> specification;

    public BaseService() {
        this.specification = new CoreSpecification<>();
    }

    @Override
    public Slice<T> findAll(Specification<T> specification, Pageable pageable) {
        Validate.notNull(pageable, fieldMissing("pageble"));

        return repository.findAll(specification, pageable);
    }

    @Override
    public T findById(Integer id) {
        Validate.notNull(id, fieldMissing("id"));

        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Override
    public T save(T t) {
        Validate.notNull(t, fieldMissing("entity"));

        if (t.getId() != null && repository.findById(t.getId()).isPresent()) {
            throw new IllegalArgumentException("Entity already exists with id: " + t.getId());
        }

        t.completeRelationships();

        return repository.save(t);

    }

    @Override
    public T update(T t) {
        Validate.notNull(t, fieldMissing("entity"));
        Validate.notNull(t.getId(), fieldMissing("id"));

        t.completeRelationships();

        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        Validate.notNull(t, fieldMissing("entity"));
        Validate.notNull(t.getId(), fieldMissing("id"));

        repository.delete(t);
    }

    protected String fieldMissing(String field) {
        return "Mandatory parameter is missing: " + field;
    }

}
