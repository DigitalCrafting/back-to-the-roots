package org.digitalcrafting.eregold.api.registration;

import lombok.Data;

@Data
public class RegisterResponse {
    private String customerId;
    private RegistrationStatusEnum status;

    public RegisterResponse(String customerId) {
        this(RegistrationStatusEnum.CREATED);
        this.customerId = customerId;
    }

    public RegisterResponse(RegistrationStatusEnum status) {
        this.status = status;
    }
}
