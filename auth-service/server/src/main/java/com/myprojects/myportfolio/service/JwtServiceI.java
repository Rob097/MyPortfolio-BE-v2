package com.myprojects.myportfolio.service;

import com.myprojects.myportfolio.clients.auth.AuthenticatedUserClaims;

public interface JwtServiceI {

    void validateToken(String token);

    AuthenticatedUserClaims getAuthorities(String token);

}
