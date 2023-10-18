package com.myprojects.myportfolio.auth.service;

import com.myprojects.myportfolio.clients.auth.ApplicationUserRole;
import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Provider;
import com.myprojects.myportfolio.auth.clients.UserClient;
import com.myprojects.myportfolio.auth.dao.AuthenticationUser;
import com.myprojects.myportfolio.auth.dao.DBRole;
import com.myprojects.myportfolio.auth.dao.DBUser;
import com.myprojects.myportfolio.auth.dto.SignINRequest;
import com.myprojects.myportfolio.auth.mapper.CoreUserMapper;
import com.myprojects.myportfolio.auth.repository.IAuthenticationUserRepository;
import com.myprojects.myportfolio.auth.repository.IRoleRepository;
import com.myprojects.myportfolio.auth.security.JwtConfig;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service(value = "applicationUserService")
@Transactional
public class AuthenticationUserService implements AuthenticationUserServiceI {

    private final IAuthenticationUserRepository applicationUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final IRoleRepository roleRepository;

    private final UserClient userClient;

    private final CoreUserMapper coreUserMapper;

    private final Provider<AuthenticationManager> authenticationManagerProvider;

    private final JwtConfig jwtConfig;

    private final SecretKey secretKey;

    @Autowired
    public AuthenticationUserService(IAuthenticationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder, IRoleRepository roleRepository, UserClient userClient, CoreUserMapper coreUserMapper, Provider<AuthenticationManager> authenticationManagerProvider, JwtConfig jwtConfig, SecretKey secretKey) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userClient = userClient;
        this.coreUserMapper = coreUserMapper;
        this.authenticationManagerProvider = authenticationManagerProvider;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        DBUser user = applicationUserRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new AuthenticationUser(user);
    }

    /**Method used to check if an id is the same as the one of the current logged-in user*/
    @Override
    public boolean hasId(Integer id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DBUser user = this.applicationUserRepository.findByEmail(username);
        return user != null && user.getId() != null && user.getId().equals(id);

    }

    @Override
    public DBUser loadUserById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<DBUser> user = this.applicationUserRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("No user found with  id: " + id));
    }

    @Override
    public String authenticateUser(SignINRequest loginRequest) {
        Authentication authenticate = authenticationManagerProvider.get().authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        Integer expirationAfterDays = jwtConfig.getTokenExpirationAfterDays();
        if (loginRequest.getRememberMe() != null && loginRequest.getRememberMe()) {
            expirationAfterDays = jwtConfig.getTokenExpirationAfterDaysRememberMe();
        }

        AuthenticationUser applicationUser = (AuthenticationUser) authenticate.getPrincipal();
        DBUser dbUser = applicationUser.getDBUser();

        String token = Jwts.builder()
                .signWith(secretKey)
                .issuedAt(new Date())
                .expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(expirationAfterDays)))
                .subject(authenticate.getName())
                .claim("firstName", dbUser.getFirstName())
                .claim("lastName", dbUser.getLastName())
                .claim("roles", applicationUser.getRolesName())
                .claim("authorities", applicationUser.getAuthoritiesName())
                .compact();

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return token;
    }

    @Override
    public DBUser registerUser(DBUser userToRegister) {
        Validate.notNull(userToRegister, "No valid user to register was provided.");

        DBUser user = applicationUserRepository.findByEmail(userToRegister.getEmail());
        Validate.isTrue(user == null, "It already exists a user with this email.");

        String encodedPsw = this.passwordEncoder.encode(userToRegister.getPassword());
        userToRegister.setPassword(encodedPsw);

        // If the user doesn't have any roles, We assign the BASIC role.
        if (userToRegister.getRoles() == null || userToRegister.getRoles().isEmpty()) {
            DBRole basicRole = this.roleRepository.findByName(ApplicationUserRole.BASIC.getName());
            userToRegister.setRoles(Set.of(basicRole));
        }

        // Try at max 10 times to get a valid id from core and save the user both in the Core DB and in the Auth DB with the same id
        int maxTries = 10;
        int tries = 0;
        ResponseEntity<MessageResource<Integer>> nextId = userClient.getNextId();
        while (nextId == null || nextId.getBody() == null || nextId.getBody().getContent() == null || this.applicationUserRepository.findById(nextId.getBody().getContent()).isPresent()) {
            nextId = userClient.getNextId();
            tries++;
            if (tries > maxTries) {
                throw new RuntimeException("No valid id was found.");
            }
        }

        userToRegister.setId(nextId.getBody().getContent());
        DBUser registeredUser = this.applicationUserRepository.saveAndFlush(userToRegister);

        // I need to update the user in the Core DB
        userClient.create(coreUserMapper.map(registeredUser));

        return registeredUser;
    }

    @Override
    public void deleteUser(DBUser userToDelete) {
        Validate.notNull(userToDelete, "No valid user to delete was provided.");

        this.applicationUserRepository.delete(userToDelete);

        userClient.delete(userToDelete.getId());
    }

    @Override
    public DBUser updateUser(DBUser userToUpdate, List<PatchOperation> operations) throws Exception {
        Validate.notNull(userToUpdate, "Mandatory parameter is missing: user.");
        Validate.notNull(userToUpdate.getId(), "Mandatory parameter is missing: id user.");

        DBUser savedUser = this.applicationUserRepository.save(userToUpdate);

        // check if I need to update the user in the Core DB
        if (operations != null) {
            userClient.patch(userToUpdate.getId(), operations);
        }

        return savedUser;

    }
}
