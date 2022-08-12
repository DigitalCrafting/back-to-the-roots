package org.digitalcrafting.eregold.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.digitalcrafting.eregold.utils.EregoldJWTUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class EregoldAuthenticationProvider implements AuthenticationProvider {

    private final EregoldJWTUtils jwtUtils;
    private final EregoldSessionContext sessionContext;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof EregoldAuthentication) {
            EregoldAuthentication eregoldAuthentication = (EregoldAuthentication) authentication;
            String token = ((EregoldAuthentication) authentication).getToken();
            if (isTokenValid(token)) {
                eregoldAuthentication.setPrincipal(new EregoldPrincipal(jwtUtils.getUserId(token)));
                eregoldAuthentication.setGrantedAuthorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
                eregoldAuthentication.setAuthenticated(true);
                return eregoldAuthentication;
            }
        }
        throw new BadCredentialsException("User not authenticated");
    }

    private boolean isTokenValid(String token) {
        if (sessionContext.getToken() != null && sessionContext.getToken().equals(token)) {
            return jwtUtils.validate(token) && jwtUtils.getUserId(token).equals(sessionContext.getUserId());
        }
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EregoldAuthentication.class.equals(aClass);
    }
}
