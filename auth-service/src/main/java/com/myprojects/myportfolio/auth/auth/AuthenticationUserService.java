package com.myprojects.myportfolio.auth.auth;

import com.myprojects.myportfolio.auth.dto.mapper.DBUserMapper;
import com.myprojects.myportfolio.clients.auth.ApplicationUserRole;
import com.myprojects.myportfolio.clients.general.PatchOperation;
import com.myprojects.myportfolio.clients.user.UserClient;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service(value="applicationUserService")
@Transactional
public class AuthenticationUserService implements UserDetailsService {

    private final IAuthenticationUserRepository applicationUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private DBUserMapper dbUserMapper;

    @Autowired
    public AuthenticationUserService(IAuthenticationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        DBUser user = applicationUserRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        AuthenticationUser apUser = new AuthenticationUser(user);
        return apUser;
    }

    /**Method used to check if an id is the same as the one of the current logged-in user*/
    public boolean hasId(Integer id){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DBUser user = this.applicationUserRepository.findByEmail(username);
        return user.getId().equals(id);

    }

    public DBUser loadUserById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<DBUser> user = this.applicationUserRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("No user found with  id: " + id));
    }

    public DBUser registerUser(DBUser userToRegister) {
        Validate.notNull(userToRegister, "No valid user to register was provided.");

        DBUser user = applicationUserRepository.findByEmail(userToRegister.getEmail());
        Validate.isTrue(user==null, "It already exists a user with this email.");

        String encodedPsw = this.passwordEncoder.encode(userToRegister.getPassword());
        userToRegister.setPassword(encodedPsw);

        // If the user doesn't have any roles, We assign the BASIC role.
        if(userToRegister.getRoles()==null || userToRegister.getRoles().isEmpty()){
            DBRole basicRole = this.roleRepository.findByName(ApplicationUserRole.BASIC.getName());
            userToRegister.setRoles(Set.of(basicRole));
        }

        DBUser registeredUser = this.applicationUserRepository.saveAndFlush(userToRegister);

        // I need to update the user in the Core DB
        userClient.create(dbUserMapper.map(registeredUser));

        return registeredUser;

    }

    public void deleteUser(DBUser userToDelete) {
        Validate.notNull(userToDelete, "No valid user to delete was provided.");

        this.applicationUserRepository.delete(userToDelete);

        userClient.delete(userToDelete.getId());
    }

    public DBUser updateUser(DBUser userToUpdate) throws Exception {
        return this.updateUser(userToUpdate, null);
    }
    public DBUser updateUser(DBUser userToUpdate, List<PatchOperation> operations) throws Exception {
        Validate.notNull(userToUpdate, "Mandatory parameter is missing: user.");
        Validate.notNull(userToUpdate.getId(), "Mandatory parameter is missing: id user.");

        DBUser savedUser = this.applicationUserRepository.save(userToUpdate);

        // check if I need to update the user in the Core DB
        if(operations!=null) {
            userClient.patch(userToUpdate.getId().intValue(), operations);
        }

        return savedUser;

    }
}
