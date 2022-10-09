package com.myprojects.myportfolio.auth.auth;

import com.myprojects.myportfolio.clients.auth.ApplicationUserRole;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class AuthenticationUserService implements UserDetailsService {

    private final IAuthenticationUserRepository applicationUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleRepository roleRepository;

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

    public DBUser registerUser(DBUser userToRegister) {
        Validate.notNull(userToRegister, "Utente da registrare non valido.");

        DBUser user = applicationUserRepository.findByEmail(userToRegister.getEmail());
        Validate.isTrue(user==null, "Esiste già un utente registrato con questa email.");

        String encodedPsw = this.passwordEncoder.encode(userToRegister.getPassword());
        userToRegister.setPassword(encodedPsw);

        // If the user doesn't have any roles, We assign the BASIC role.
        if(userToRegister.getRoles()==null || userToRegister.getRoles().isEmpty()){
            DBRole basicRole = this.roleRepository.findByName(ApplicationUserRole.BASIC.getName());
            userToRegister.setRoles(Set.of(basicRole));
        }

        return this.applicationUserRepository.saveAndFlush(userToRegister);

    }

    public void deleteUser(DBUser userToDelete) {
        this.applicationUserRepository.delete(userToDelete);
    }
}
