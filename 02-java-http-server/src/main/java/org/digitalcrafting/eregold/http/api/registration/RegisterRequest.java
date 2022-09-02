package org.digitalcrafting.eregold.http.api.registration;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private char[] password;
    private String firstName;
    private String lastName;
}
