package org.digitalcrafting.eregold.http.core.session;

import lombok.Data;

@Data
public class DCUserContext {
    private String userId;
    private String customerId;
    private String token;
}
