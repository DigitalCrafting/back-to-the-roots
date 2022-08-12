package org.digitalcrafting.eregold.authentication;

import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;

public class EregoldAuthenticationFilter extends AuthenticationFilter {
    public EregoldAuthenticationFilter(EregoldAuthenticationProvider authenticationProvider, EregoldAuthenticationConverter authenticationConverter) {
        super(new ProviderManager(authenticationProvider), authenticationConverter);
        super.setSuccessHandler((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });
    }
}
