package com.myprojects.myportfolio.core.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserServiceI {

    Slice<User> findAll(Specification<?> specification, Pageable pageable);

    User findById(Integer id);

    User findBySlug(String slug);

    User findByEmail(String email);

    User save(User userToSave);

    User update(User userToUpdate);

    void delete(User userToDelete);

    boolean hasId(Integer id);

    User getCurrentLoggedInUser();

}
