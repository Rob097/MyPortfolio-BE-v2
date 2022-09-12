package com.myprojects.myportfolio.clients.auth;

import org.assertj.core.util.Strings;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtTokenVerifier {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private RouterValidator routerValidator;

    public JwtTokenVerifier(SecretKey secretKey,
                            JwtConfig jwtConfig,
                            RouterValidator routerValidator) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.routerValidator = routerValidator;
    }

    /*
     * TODO: Aggiungere una cache da 1 minuto (Request Scoped) perché ogni microservizio richiamerà questo metodo.
     *  Quindi se un ms chiama un altro mc il metodo viene chiamato 2 volte in pochi secondi
     */
    public void validateToken(HttpServletRequest request) throws ResponseStatusException{

        if(request == null){
            throw new IllegalStateException("Request cannot be null.");
        }

        if (routerValidator.isHttpServletRequestSecured.test(request)) {
            String internalAuthorizationHeader = request.getHeader(jwtConfig.getInternalAuthorizationHeader());
            String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
            String message = null;

            if(Strings.isNullOrEmpty(authorizationHeader)  || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())){
                message = "You need to be authenticated to access this resource.";
            }
            else if (Strings.isNullOrEmpty(internalAuthorizationHeader)  || !internalAuthorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
                message = "You're trying to access this microservice without passing through the load balancer.";
            } else {

                String token = internalAuthorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
                if (jwtConfig.isInvalid(token)) {
                    message = "Authorization header is expired";
                }

            }

            if(message!=null)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);

        }

    }

}
