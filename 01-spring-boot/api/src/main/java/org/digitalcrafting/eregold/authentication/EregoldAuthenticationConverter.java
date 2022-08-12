package org.digitalcrafting.eregold.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class EregoldAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            EregoldAuthentication eregoldAuthentication = new EregoldAuthentication();
            eregoldAuthentication.setToken(token);
            return eregoldAuthentication;
        } else {
            return null;
        }
    }
}
