package org.digitalcrafting.eregold.authentication;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EregoldSessionContext {
    // TODO either change userId to email or the other way around, it makes sense to name them like that, but it's annoying to work with
    private String userId;
    private String customerId;
    private String token;
}
