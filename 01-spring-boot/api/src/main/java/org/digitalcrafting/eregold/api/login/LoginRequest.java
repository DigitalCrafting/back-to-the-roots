package org.digitalcrafting.eregold.api.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotBlank
    private String userId;
    @NotNull
    private char[] password;
}
