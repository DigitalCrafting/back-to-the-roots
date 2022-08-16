package org.digitalcrafting.eregold.http.repository.customers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerEntity {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
}
