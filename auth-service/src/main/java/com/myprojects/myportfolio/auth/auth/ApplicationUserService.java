package com.myprojects.myportfolio.auth.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplicationUserService implements UserDetailsService {

    private final IApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserService(IApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        DBUser user = applicationUserRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        ApplicationUser apUser = new ApplicationUser(user);
        return apUser;
    }
}
