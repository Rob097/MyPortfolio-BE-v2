package com.myprojects.myportfolio.service;

import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.dao.DBUser;
import com.myprojects.myportfolio.dto.SignINRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AuthenticationUserServiceI extends UserDetailsService {

    boolean hasId(Integer id);

    DBUser loadUserById(Integer id);

    String authenticateUser(SignINRequest loginRequest);

    DBUser registerUser(DBUser userToRegister);

    void deleteUser(DBUser userToDelete);

    DBUser updateUser(DBUser userToUpdate, List<PatchOperation> operations) throws Exception;

}
