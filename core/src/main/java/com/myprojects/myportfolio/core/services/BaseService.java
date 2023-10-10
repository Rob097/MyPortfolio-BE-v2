package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.clients.general.specifications.CoreSpecification;
import com.myprojects.myportfolio.core.dao.BaseDao;
import com.myprojects.myportfolio.core.repositories.BaseRepository;
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
        checkIfEntityAlreadyExists(t.getId());

        // We connect all relationships with BaseDao completeRelationships method in PrePersist

        return repository.save(t);

    }

    @Override
    public T update(T t) {
        Validate.notNull(t, fieldMissing("entity"));
        Validate.notNull(t.getId(), fieldMissing("id"));
        checkIfEntityDoesNotExist(t.getId());

        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        Validate.notNull(t, fieldMissing("entity"));
        Validate.notNull(t.getId(), fieldMissing("id"));

        // We disconnect all relationships with BaseDao removeRelationships method in PreRemove

        repository.delete(t);
    }

    protected void checkIfEntityAlreadyExists(Integer id) {
        if (id != null && repository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Entity already exists with id: " + id);
        }
    }

    protected void checkIfEntityDoesNotExist(Integer id) {
        if (id == null || repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Entity does not exist" + (id != null ? (" with id: " + id) : "."));
        }
    }

    protected String fieldMissing(String field) {
        return "Mandatory parameter is missing: " + field;
    }

}
