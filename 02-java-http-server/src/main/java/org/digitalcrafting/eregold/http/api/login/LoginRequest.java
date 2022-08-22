package org.digitalcrafting.eregold.http.api.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String userId;
    private char[] password;
}
