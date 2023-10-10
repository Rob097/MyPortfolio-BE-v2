package com.myprojects.myportfolio.dao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class AuthenticationUser implements UserDetails {

    private final DBUser user;
    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    public AuthenticationUser(DBUser user) {
        this.user = user;
        this.grantedAuthorities = user.getRoles().stream().flatMap(el -> el.getPermissions().stream()).collect(Collectors.toSet());
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public DBUser getDBUser() {
        return user;
    }

    public List<String> getAuthoritiesName() {
        Set<DBPermission> permissions = (Set<DBPermission>) this.getAuthorities();
        List<String> names = new ArrayList<>();

        if (permissions != null && !permissions.isEmpty()) {
            names.addAll(permissions.stream().map(DBPermission::getAuthority).toList());
        }

        return names;

    }

    public List<String> getRolesName() {
        Set<DBRole> roles = this.user.getRoles();
        List<String> names = new ArrayList<>();

        if (roles != null && !roles.isEmpty()) {
            names.addAll(roles.stream().map(DBRole::getName).toList());
        }

        return names;

    }
}
