package org.digitalcrafting.eregold.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

@Data
@AllArgsConstructor
public class EregoldPrincipal implements Principal {
    private String userId;

    @Override
    public String getName() {
        return userId;
    }
}
